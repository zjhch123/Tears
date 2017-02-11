package com.zjh.tears.config;

import com.zjh.tears.filter.socket.SocketFilter;
import com.zjh.tears.filter.socket.HeaderSocketFilter;
import com.zjh.tears.handler.HeaderHTTPHandler;
import com.zjh.tears.handler.HTTPHandler;
import com.zjh.tears.listener.socket.HeaderSocketListener;
import com.zjh.tears.listener.socket.SocketListener;
import com.zjh.tears.util.ClassParse;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.util.stream.Collectors;

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

    public static List<String> DEFAULT_INDEX = new ArrayList<>();
    public static Map<Integer, String> ERR_PAGES = new HashMap<>();
    public static Map<String, String> STATIC_FILE_STRATEGYS = new HashMap<>();

    public static String STATIC_ROOT_FILE;

    public static String DEFAULT_CHARSET;

    static {

        File configFile = new File(Config.class.getClassLoader().getResource("config.json").getFile());
        try {
            JSONObject config = new JSONObject(Files.readAllLines(configFile.toPath()).stream().collect(Collectors.joining()));
            JSONObject serverConfig = config.getJSONObject("serverConfig");
            JSONObject pageConfig = config.getJSONObject("pageConfig");
            Config.INIT_SERVER_CONFIG(serverConfig);
            Config.INIT_PAGE_CONFIG(pageConfig);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }


    }

    private static final void INIT_SERVER_CONFIG(final JSONObject serverConfig) throws Exception {
        Config.PORT = serverConfig.getInt("port");
        Config.THREAD_POOL_SIZE = serverConfig.getInt("threadPoolSize");
        Config.SERVER_NAME = serverConfig.getString("serverName");
        Config.STATIC_ROOT_FILE = serverConfig.getString("staticRootFile");
        Config.DEFAULT_CHARSET = serverConfig.getString("defaultCharset");
        SocketFilter headerFilter = Config.SOCKET_FILTER_HEADER;
        for (Object obj : serverConfig.getJSONArray("socketFilter")) {
            String className = (String) obj;
            SocketFilter filter = (SocketFilter) ClassParse.getInstance().getObject(className);
            headerFilter.setNext(filter);
            headerFilter = filter;
        }

        SocketListener headerListener = Config.SOCKET_LISTENER_HEADER;
        for (Object obj : serverConfig.getJSONArray("socketListener")) {
            String className = (String) obj;
            SocketListener listener = (SocketListener) ClassParse.getInstance().getObject(className);
            headerListener.setNext(listener);
            headerListener = listener;
        }

        HTTPHandler headerHandler = Config.HTTP_HANDLER_HEADER;
        for (Object obj : serverConfig.getJSONArray("httpHandler")) {
            String className = (String) obj;
            HTTPHandler handler = (HTTPHandler) ClassParse.getInstance().getObject(className);
            headerHandler.setNext(handler);
            headerHandler = handler;
        }

        for (Object obj : serverConfig.getJSONArray("staticFileStrategy")) {
            JSONObject strategy = (JSONObject) obj;
            STATIC_FILE_STRATEGYS.put(strategy.getString("name"), strategy.getString("strategy"));
        }
    }

    private static final void INIT_PAGE_CONFIG(final JSONObject pageConfig) {
        for(Object obj : pageConfig.getJSONArray("defaultIndex")) {
            String index = (String) obj;
            Config.DEFAULT_INDEX.add(index);
        }
        for (Object obj : pageConfig.getJSONArray("errorPages")) {
            JSONObject errorPage = (JSONObject) obj;
            Config.ERR_PAGES.put(errorPage.getInt("code"), errorPage.getString("page"));
        }
    }
}
