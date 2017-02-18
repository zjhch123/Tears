package com.zjh.tears.config;

import com.zjh.tears.filter.socket.SocketFilter;
import com.zjh.tears.filter.socket.HeaderSocketFilter;
import com.zjh.tears.handler.HeaderHTTPHandler;
import com.zjh.tears.handler.HTTPHandler;
import com.zjh.tears.listener.socket.HeaderSocketListener;
import com.zjh.tears.listener.socket.SocketListener;
import com.zjh.tears.model.ReverseProxy;
import com.zjh.tears.util.ClassParse;
import com.zjh.tears.util.Util;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public final class Config {

    public static Integer PORT;
    public static Integer THREAD_POOL_SIZE;
    public static SocketFilter SOCKET_FILTER_HEADER = new HeaderSocketFilter();
    public static SocketListener SOCKET_LISTENER_HEADER = new HeaderSocketListener();
    public static HTTPHandler HTTP_HANDLER_HEADER = new HeaderHTTPHandler();

    public static String SERVER_NAME;

    public static boolean REVERSE_PROXY_USAGE = false;

    public static List<String> DEFAULT_INDEX = new ArrayList<>();
    public static Map<Integer, String> ERR_PAGES = new HashMap<>();
    public static Map<String, String> STATIC_FILE_STRATEGYS = new HashMap<>();

    public static Map<String, ReverseProxy> REVERSE_PROXY_MAPPING = new HashMap<>();

    public static String STATIC_ROOT_FILE;

    public static String DEFAULT_CHARSET;

    public static boolean ACCEPT_CONFIG_USAGE = false;
    public static Set<Pattern> ACCEPT_FILE = new HashSet<>();
    public static Set<Pattern> EXCEPT_FILE = new HashSet<>();

    private static Logger logger = Logger.getLogger(Config.class);

    static {
        logger.debug("Load Config File...");
        File configFile = new File(Config.class.getClassLoader().getResource("config.json").getFile());
        try {
            long startTime = System.currentTimeMillis();
            logger.debug("\n _____   _____       ___   _____    _____  \n" +
                    "|_   _| | ____|     /   | |  _  \\  /  ___/ \n" +
                    "  | |   | |__      / /| | | |_| |  | |___  \n" +
                    "  | |   |  __|    / / | | |  _  /  \\___  \\ \n" +
                    "  | |   | |___   / /  | | | | \\ \\   ___| | \n" +
                    "  |_|   |_____| /_/   |_| |_|  \\_\\ /_____/  - zjh\n");
            JSONObject config = new JSONObject(Files.readAllLines(configFile.toPath()).stream().collect(Collectors.joining()));
            logger.debug("Load Config File SUCCESS!");
            logger.debug("Setting...");
            JSONObject serverConfig = config.getJSONObject("serverConfig");
            JSONObject pageConfig = config.getJSONObject("pageConfig");
            JSONObject acceptConfig = config.getJSONObject("acceptConfig");
            Config.INIT_SERVER_CONFIG(serverConfig);
            Config.INIT_PAGE_CONFIG(pageConfig);
            Config.INIT_ACCEPT_CONFIG(acceptConfig);
            long endTime = System.currentTimeMillis();
            logger.debug("Config SUCCESS! at " + (endTime - startTime) + "ms");
            logger.debug("Server start...");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(Util.stackTraceToString(e));
            System.exit(-1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(Util.stackTraceToString(e));
            System.exit(-1);
        }
    }

    private static final void INIT_SERVER_CONFIG(final JSONObject serverConfig) throws Exception {
        Config.PORT = serverConfig.getInt("port");
        logger.debug("server port - " + Config.PORT);
        Config.THREAD_POOL_SIZE = serverConfig.getInt("threadPoolSize");
        logger.debug("server thread pool size - " + Config.THREAD_POOL_SIZE);
        Config.SERVER_NAME = serverConfig.getString("serverName");
        logger.debug("server name - " + Config.SERVER_NAME);
        Config.STATIC_ROOT_FILE = serverConfig.getString("staticRootFile");
        logger.debug("static root file - " + Config.STATIC_ROOT_FILE);
        Config.DEFAULT_CHARSET = serverConfig.getString("defaultCharset");
        logger.debug("default charset - " + Config.DEFAULT_CHARSET);
        SocketFilter headerFilter = Config.SOCKET_FILTER_HEADER;
        logger.debug("Init Socket Filter...");
        for (Object obj : serverConfig.getJSONArray("socketFilter")) {
            String className = (String) obj;
            logger.debug("init class - " + className);
            SocketFilter filter = (SocketFilter) ClassParse.getInstance().getObject(className);
            headerFilter.setNext(filter);
            headerFilter = filter;
        }
        logger.debug("Init Socket Filter SUCCESS!");
        SocketListener headerListener = Config.SOCKET_LISTENER_HEADER;
        logger.debug("Init Socket Listener...");
        for (Object obj : serverConfig.getJSONArray("socketListener")) {
            String className = (String) obj;
            logger.debug("init class - " + className);
            SocketListener listener = (SocketListener) ClassParse.getInstance().getObject(className);
            headerListener.setNext(listener);
            headerListener = listener;
        }
        logger.debug("Init Socket Listener SUCCESS!");
        HTTPHandler headerHandler = Config.HTTP_HANDLER_HEADER;
        logger.debug("Init HTTP Handler...");
        for (Object obj : serverConfig.getJSONArray("httpHandler")) {
            String className = (String) obj;
            logger.debug("init class - " + className);
            HTTPHandler handler = (HTTPHandler) ClassParse.getInstance().getObject(className);
            headerHandler.setNext(handler);
            headerHandler = handler;
        }
        logger.debug("Init HTTP Handler SUCCESS!");
        logger.debug("Init Static File Strategy...");
        for (Object obj : serverConfig.getJSONArray("staticFileStrategy")) {
            JSONObject strategy = (JSONObject) obj;
            String strategyName = strategy.getString("name");
            String strategyClass = strategy.getString("strategy");
            logger.debug("init strategy - " + strategyName + ", class - " + strategyClass);
            STATIC_FILE_STRATEGYS.put(strategyName, strategyClass);
        }

        JSONObject reverseProxy = serverConfig.getJSONObject("reverseProxy");
        Config.REVERSE_PROXY_USAGE = reverseProxy.optBoolean("usage");
        logger.debug("reverse proxy usage? - " + Config.REVERSE_PROXY_USAGE );
        if(Config.REVERSE_PROXY_USAGE) {
            for(Object obj : reverseProxy.getJSONArray("proxy")) {
                JSONObject reverseJSON = (JSONObject) obj;
                String rule = "/" + reverseJSON.getString("rule").replaceAll("\\.", "\\\\.").replaceAll("\\*", "((?![\\\\./]).)*");
                String target = reverseJSON.getString("target");
                ReverseProxy reverseProxyObj = new ReverseProxy(rule, target);
                for(Object reverseProxyCookieObj : reverseJSON.getJSONArray("reverseProxyCookie")) {
                    JSONObject cookieMapping = (JSONObject) reverseProxyCookieObj;
                    String from = cookieMapping.getString("from");
                    String to = cookieMapping.getString("to");
                    reverseProxyObj.addCookieMapping(from, to);
                }
                Config.REVERSE_PROXY_MAPPING.put(rule, reverseProxyObj);
            }
        }
    }

    private static final void INIT_PAGE_CONFIG(final JSONObject pageConfig) {
        for (Object obj : pageConfig.getJSONArray("defaultIndex")) {
            String index = (String) obj;
            Config.DEFAULT_INDEX.add(index);
        }
        logger.debug("set default Index - " + Config.DEFAULT_INDEX);
        for (Object obj : pageConfig.getJSONArray("errorPages")) {
            JSONObject errorPage = (JSONObject) obj;
            int code = errorPage.getInt("code");
            String page = errorPage.getString("page");
            Config.ERR_PAGES.put(code, page);
            logger.debug("set error Page - code " + code + ", page " + page);
        }
    }

    private static final void INIT_ACCEPT_CONFIG(final JSONObject acceptConfig) {
        Config.ACCEPT_CONFIG_USAGE = acceptConfig.optBoolean("usage", false);
        logger.debug("accept config usage? - " + Config.ACCEPT_CONFIG_USAGE);
        if (Config.ACCEPT_CONFIG_USAGE) {
            JSONArray accept = acceptConfig.getJSONArray("accept");
            logger.debug("load accept path pattern - " + accept);
            JSONArray except = acceptConfig.getJSONArray("except");
            logger.debug("load except path pattern - " + except);
            Config.initFile(true, accept);
            Config.initFile(false, except);
        }
    }

    private static void initFile(boolean isAccept, JSONArray fileList) {
        for (Object obj : fileList) {
            String file = (String) obj;
            String path = file.replaceAll("\\.", "\\\\.").replaceAll("\\*", "((?![\\\\./]).)*");
            if(isAccept) {
                Config.ACCEPT_FILE.add(Pattern.compile(path));
            } else{
                Config.EXCEPT_FILE.add(Pattern.compile(path));
            }
        }
    }

}
