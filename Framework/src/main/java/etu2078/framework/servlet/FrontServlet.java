package etu2078.framework.servlet;
import etu2078.framework.Mapping;
import etu2078.framework.ModelView;
import etu2078.framework.annotation.*;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class FrontServlet extends HttpServlet {

    HashMap<String, Mapping> MappingUrls;
    HashMap<String,Object> instances = new HashMap<String,Object>();

    public HashMap<String, Mapping> getMappingUrls() {
        return MappingUrls;
    }

    public HashMap<String,Object> getInstances()
    {
        return instances;
    }

    public void setInstances(HashMap<String, Object> instances) {
        this.instances = instances;
    }

    public void setMappingUrls(HashMap<String, Mapping> mappingUrls) {
        MappingUrls = mappingUrls;
    }

    @Override
    public void init() throws ServletException {
        HashMap<String, Mapping> map = new HashMap<>();

        String path=getInitParameter("PATH");
        Vector<String> liste=getClasses(path, "model");




        for(String nom:liste)
        {
            Class<?> cl= null;
            try {
                cl = Class.forName(nom);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            Method[] methods= cl.getDeclaredMethods();
            for (Method method:methods)
            {
                if(method.isAnnotationPresent(Url.class))
                {
                    Mapping value1 = new Mapping(nom,method.getName());
                    map.put(method.getAnnotation(Url.class).urlName(), value1);
                }
            }
        }
        setMappingUrls(map);
    }
    public static Vector<String> getClasses(String path,String p)
    {
        File file=new File(path);
        String[] liste=file.list();
        Vector<String> reponse=new Vector<>();
        for (int i=0;i< liste.length;i++)
        {
            if(liste[i].endsWith(".java"))
            {
                reponse.add(p+"."+liste[i].split(".java")[0]);
            }
        }
        return reponse;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            this.processRequest(request,response);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            this.processRequest(request,response);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void processRequest(HttpServletRequest request,HttpServletResponse response) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ServletException {

        Gson gson = new GsonBuilder().create();

        String lien= String.valueOf(request.getRequestURL());
        String[] mots=lien.split("/",5);
        PrintWriter out = response.getWriter();


        String sessionName = getInitParameter("sessionName");
        String sessionProfil = getInitParameter("sessionProfil");




        String className =  Class.forName(getMappingUrls().get(mots[mots.length-1]).getClassName()).getName();

        Object object = FrontServlet.instance(className,this.getInstances());

        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String setterMethodName = "";
            if(paramName.endsWith("[]"))
            {
                String temp = paramName.replace("[]", "");
                setterMethodName = "set" + temp.substring(0, 1).toUpperCase() + temp.substring(1);
            }else {
                setterMethodName = "set" + paramName.substring(0, 1).toUpperCase() +paramName.substring(1);
            }




            Class<?> paramType = determineParameterType(object.getClass(), setterMethodName);
            if (paramType == null) {
                continue;
            }
            Method setterMethod = object.getClass().getMethod(setterMethodName, paramType);


            Object paramValue = convertParameterValue(request.getParameter(paramName), paramType);

            setterMethod.invoke(object, paramValue);

        }

        String metName = getMappingUrls().get(mots[mots.length-1]).getMethod();
        Method[] allMeth = object.getClass().getDeclaredMethods();
        Method met = null;

        for ( Method m: allMeth){
            if(m.getName().compareTo(metName)==0)
            {
                met = m;
            }
        }

        HttpSession session = request.getSession();
        //boolean isCo = (boolean)session.getAttribute(sessionName);
        Parameter[] allP = met.getParameters();


        Object[] arg = new Object[allP.length];
        int it = 0;
        for (Parameter parameter : allP) {
            Param annotation = parameter.getAnnotation(Param.class);
            if (annotation != null) {
                String annotationValue = annotation.paramAttribut();
                String getterMethodName = "get" + annotationValue.substring(0, 1).toUpperCase() + annotationValue.substring(1);
                Method tempM = object.getClass().getMethod(getterMethodName);
                arg[it] = tempM.invoke(object);
            }
            it++;
        }

        HttpSession s = request.getSession();

        ModelView modelView = null;

        if(met.isAnnotationPresent(Authentification.class)){
            try{
                if((boolean) s.getAttribute(sessionName)==true)
                {
                    try {
                        if (met.getAnnotation(Authentification.class).profil().compareTo((String) session.getAttribute(sessionProfil)) == 0) {
                            out.println("haha2");

                            if(met.isAnnotationPresent(RestAPI.class)){
                                Object ob = met.invoke(object);
                                String json = gson.toJson(ob);
                                out.println(json);
                            }

                            if (arg.length > 0) {
                                modelView = (ModelView) met.invoke(object, arg);
                            } else {
                                modelView = (ModelView) met.invoke(object);
                            }
                            if (modelView.getData() != null) {
                                for (Map.Entry<String, Object> entry : modelView.getData().entrySet()) {
                                    String key = entry.getKey();
                                    Object value = entry.getValue();
                                    request.setAttribute(key, value);
                                }
                            }
                            if(modelView.isJson()){ out.println("atooo");String json = gson.toJson(modelView.getData());out.println(json);}
                            if (modelView.getSession() != null) {
                                for (Map.Entry<String, Object> entry : modelView.getSession().entrySet()) {
                                    String key = entry.getKey();
                                    Object value = entry.getValue();
                                    s.setAttribute(key, value);
                                }
                            }

                           //RequestDispatcher dispat = request.getRequestDispatcher(modelView.getUrl());
                           //dispat.forward(request, response);
                        }
                    }catch (Exception e){
                        out.println("non authoriser");
                    }
                }
            }catch (Exception e) {
                out.println("tsy connecté");
            }
        }else {
            if(met.isAnnotationPresent(RestAPI.class)){
                Object ob = met.invoke(object);
                String json = gson.toJson(ob);
                out.println(json);
            }
            if(arg.length>0){
                modelView = (ModelView) met.invoke(object,arg);
            }else {
                modelView = (ModelView) met.invoke(object);
            }
            if(modelView.getData()!=null)
            {
                for (Map.Entry<String, Object> entry : modelView.getData().entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    request.setAttribute(key,value);
                }
            }
            if(modelView.getSession()!=null)
            {
                for (Map.Entry<String, Object> entry : modelView.getSession().entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    s.setAttribute(key,value);
                }
            }
            if(modelView.isJson()){ String json = gson.toJson(modelView.getData());
                out.println(json);}
            //RequestDispatcher dispat = request.getRequestDispatcher(modelView.getUrl());
            //dispat.forward(request,response);
        }
        if(modelView!=null)
        {
            if(modelView.isInvalidateSession())
            {
                if(modelView.getListeSession().size()>0)
                {
                    Vector<String> allS = modelView.getListeSession();
                    for (int i = 0; i <allS.size(); i++) {
                        session.removeAttribute(allS.get(i));
                    }
                    modelView.setListeSession(null);
                }
            }
        }
    }
    private Class<?> determineParameterType(Class<?> objectClass, String methodName) {
        for (Method method : objectClass.getMethods()) {
            if (method.getName().equals(methodName) && method.getParameterCount() == 1) {
                return method.getParameterTypes()[0];
            }
        }
        return null;
    }

    private Object convertParameterValue(String paramValue, Class<?> paramType) {
        if (paramType == String.class) {
            return paramValue;
        } else if (paramType == int.class || paramType == Integer.class) {
            return Integer.parseInt(paramValue);
        } else if (paramType == double.class || paramType == Double.class) {
            return Double.parseDouble(paramValue);
        } else if (paramType == Date.class) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return dateFormat.parse(paramValue);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void setAllAttributesToNull(Object obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(obj, null);
        }
    }

    public static Object instance(String className,HashMap<String,Object> instances) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Object res = null;
        int i=0;
        if(Class.forName(className).getAnnotation(Scope.class).isSingleton()==true)
        {
            for (Map.Entry<String, Object> entry : instances.entrySet()) {
                if(className.compareTo(entry.getKey())==0)
                {
                    Object value = entry.getValue();
                    FrontServlet.setAllAttributesToNull(value);
                    res = value;
                    break;
                }else {
                    i++;
                }
            }
        }else {
            res = Class.forName(className).getConstructor().newInstance();
        }
        if(i==instances.size())
        {
            res = Class.forName(className).getConstructor().newInstance();
            instances.put(className,res);
        }
        return res;
    }
}
