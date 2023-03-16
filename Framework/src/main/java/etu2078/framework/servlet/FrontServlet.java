package etu2078.framework.servlet;
import etu2078.framework.Mapping;
import etu2078.framework.annotation.Url;
import java.net.URL;
import jakarta.servlet.*;
import jakarta.servlet.http.*;



import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.*;




public class FrontServlet extends HttpServlet {

    HashMap<String, Mapping> MappingUrls;

    public HashMap<String, Mapping> getMappingUrls() {
        return MappingUrls;
    }

    public void setMappingUrls(HashMap<String, Mapping> mappingUrls) {
        MappingUrls = mappingUrls;
    }

    public static Vector<Class> getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        Vector<Class> res = new Vector<Class>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        Class[] cls =  classes.toArray(new Class[classes.size()]);
        for (int i = 0; i < cls.length; i++) {
            res.add(cls[i]);
        }
        return res;
    }
    public static Vector<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        Vector<Class> classes = new Vector<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
    @Override
    public void init() throws ServletException{
        this.setMappingUrls(new HashMap<String,Mapping>());
        try{
            Vector<Class> classes = FrontServlet.getClasses("objet");
            for(int i = 0;i<classes.size();i++){
                Class<?> classe = classes.get(i);
                Method[] methods = classe.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Url.class)) {
                        String url = method.getAnnotation(Url.class).urlName();
                        Mapping newmap = new Mapping(classe.getName(),method.getName());
                        this.getMappingUrls().put(url,newmap);
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getPathInfo().substring(1);
        PrintWriter out = response.getWriter();
        String url = request.getRequestURL().toString();

        out.println(url.split("/")[5]);

        for(Map.Entry<String,Mapping> map : this.getMappingUrls().entrySet())
        {
            String key = map.getKey();
            Mapping value = map.getValue();
            if(url.split("/")[5].equals(map.getKey()))
            {
                out.println("La methode est"+map.getValue().getMethod());
            }
        }
        if (action != null) {
            switch (action) {
                case "create":

                    break;
                case "update":

                    break;
                case "delete":

                    break;
                default:
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request,response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request,response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
