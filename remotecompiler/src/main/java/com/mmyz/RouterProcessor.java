package com.mmyz;

import com.google.auto.service.AutoService;
import com.mmyz.annotation.FieldAnnotation;
import com.mmyz.annotation.Component;
import com.mmyz.annotation.Components;
import com.mmyz.annotation.StaticRouter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor{

    private Filer mFiler;
    private Messager mMessager;

//    private Map<String, String> mStaticRouterMap = new HashMap<>();
    private List<String> mStaticRouterMap = new ArrayList<>();
    private List<String> mAutoRouterList = new ArrayList<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new HashSet<>();
        annotationTypes.add(FieldAnnotation.class.getCanonicalName());
        annotationTypes.add(StaticRouter.class.getCanonicalName());
        annotationTypes.add(Component.class.getCanonicalName());
        annotationTypes.add(Components.class.getCanonicalName());
        return annotationTypes;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        mStaticRouterMap.clear();
        mAutoRouterList.clear();

        try {
            Set<? extends Element> components=roundEnvironment.getElementsAnnotatedWith(Components.class);
            Set<? extends Element> component=roundEnvironment.getElementsAnnotatedWith(Component.class);
            if (!components.isEmpty()){
                processPatchClass(components);
                return true;
            }
            processComponentAndPatchClass(roundEnvironment);
        } catch (IOException e) {
            e.printStackTrace();
            mMessager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            return true;
        }
        return true;
    }


    private void processPatchClass(Set<? extends Element> elements) throws IOException{
        TypeElement typeElement = (TypeElement) elements.iterator().next();
        JavaFileObject javaFileObject = mFiler.createSourceFile(Config.ROUTER_OPERATOR, typeElement);
        /*
            package com.mmyz;
            public class RouterOperator{
                public static void autoSetup(){
                    Module_autoPutRouter.()
                }
            }
         */
        PrintWriter printWriter = new PrintWriter(javaFileObject.openWriter());
        printWriter.println("package "+Config.PACKAGE_NAME+";");
        printWriter.println("public class "+Config.ROUTER_OPERATOR+" {");
        printWriter.println("public static void  "+Config.ROUTER_OPERATOR_METHOD+"(){");

        Components components = typeElement.getAnnotation(Components.class);

        String[] values = components.value();
        for (String value:
             values) {
//            //// TODO: 2017/3/24
            printWriter.println(Config.FILE_PREFIX+value+".autoPutRouter();");
        }
        printWriter.println("}");
        printWriter.println("}");
        printWriter.flush();
        printWriter.close();
    }



    private void processComponentAndPatchClass(RoundEnvironment roundEnvironment) throws IOException {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Component.class);
        mMessager.printMessage(Diagnostic.Kind.NOTE, "=====>processComponentAndPatchClass"+elements);
        if (elements.isEmpty())return;

        Element item = elements.iterator().next();

        Set<? extends Element> routerElements = roundEnvironment.getElementsAnnotatedWith(StaticRouter.class);
        for (Element e :
                routerElements) {
            if (!(e instanceof TypeElement)){continue;}
            TypeElement typeElement = (TypeElement) e;
            String uri = typeElement.getAnnotation(StaticRouter.class).value();
            mStaticRouterMap.add(uri);
        }

//        Set<? extends Element> autoRouterElements = roundEnvironment.getElementsAnnotatedWith(AutoRouter.class);
//
//        for (Element e :
//                autoRouterElements) {
//            if (!(e instanceof TypeElement)){continue;}
//            TypeElement typeElement = (TypeElement) e;
//            mAutoRouterList.add(typeElement.getQualifiedName().toString());
//        }
//
        String componentName = item.getAnnotation(Component.class).value();
//        mMessager.printMessage(Diagnostic.Kind.NOTE, "=====>processComponentAndPatchClass"+componentName);
        componentPatchClass(componentName);

    }

    private void componentPatchClass(String componentName) throws IOException {
        String className = Config.FILE_PREFIX+componentName;
        JavaFileObject javaFileObject = mFiler.createSourceFile(className);

        PrintWriter printWriter = new PrintWriter(javaFileObject.openWriter());

        /*
            package com.mmyz;
            import android.app.Activity;
            import android.app.Service;
            import android.content.BroadcastReceiver;
            import android.content.BroadcastReceiver;
            import com.mmyz.routermodule.Router;
            import com.mmyz.routermodule.routerRule.ActivityRule;
            import com.mmyz.routermodule.routerRule.ReceiverRule;
            import com.mmyz.routermodule.routerRule.ServiceRule;

            public class ClassName {
                public static void autoPutRouter(){
                     Router.putRouter(uri, class);
                     if (Activity.class.isAssignableFrom(clzz.clss)){
                        Router.putRouter(ActivityRule.ACTIVITY_PROTOCOL+clazz, clazz.class);
                     }
                     if (Service.class.isAssignableFrom(clzz.class)){
                        Router.putRouter(ServiceRule.SERVICE_PROTOCOL+clazz, clazz.class);
                     }
                     if (BroadcastReceiver.class.isAssignableFrom(clzz.clss)){
                        Router.putRouter(ReceiverRule.RECEIVER_PROTOCOL+clazz, clazz.class);
                     }
                }
            }
         */
        printWriter.println("package "+Config.PACKAGE_NAME+";");
        //Activity
        printWriter.println("import android.support.v7.app.AppCompatActivity;");
        //Service
        printWriter.println("import android.app.Service;");
        //BroadcastReceiver
        printWriter.println("import android.content.BroadcastReceiver;");
        printWriter.println("import "+Config.PACKAGE_NAME+".Router;");
        printWriter.println("import "+Config.PACKAGE_NAME+".exception.NotFoundClassException;;");

        printWriter.println("public class "+ className +" {");

        printWriter.println("public static void autoPutRouter(){");
//        Router.putRouter(ActivityRule.ACTIVITY_PROTOCOL+"account.index", LoginActivity.class);
        //StaticRouter

        printWriter.println("try {");
        for (String entry : mStaticRouterMap) {
            printWriter.println("Router.putRemoteUriDefaultPattern(\""+entry+"\");");
        }
        printWriter.println("} catch (NotFoundClassException e) {");
        printWriter.println("e.printStackTrace();");
        printWriter.println("}");

//        //AutoRouter
//        for (String clazz :
//                mAutoRouterList) {
//            printWriter.println("if (Activity.class.isAssignableFrom("+clazz+".class)){");
//            printWriter.println("Router.putRouter(ActivityRule.ACTIVITY_PROTOCOL+"+clazz+", "+clazz+".class);");
//            printWriter.println("}");
//            printWriter.println("else if(Service.class.isAssignableFrom("+clazz+".class)){");
//            printWriter.println("Router.putRouter(ServiceRule.PROTOCOL+"+clazz+","+clazz+".class);");
//            printWriter.println("}");
//            printWriter.println("else if(BroadcastReceiver.class.isAssignableFrom("+clazz+".class)){");
//            printWriter.println("Router.putRouter(ReceiverRule.PROTOCOL+"+clazz+","+clazz+".class);");
//            printWriter.println("}");
//
//        }

        printWriter.println("}");
        printWriter.println("}");
        printWriter.flush();
        printWriter.close();






    }
}
