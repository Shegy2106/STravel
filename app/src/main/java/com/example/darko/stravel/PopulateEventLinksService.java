package com.example.darko.stravel;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PopulateEventLinksService extends Service {

    private ArrayList<TableEventLinks> tableEventLinks;
    private ExecutorService executorService;
    private volatile LinksDataSingleton linksDataSingleton;

    @Override
    public void onCreate() {
        executorService = Executors.newSingleThreadExecutor();
        tableEventLinks = new ArrayList<>();
        linksDataSingleton = LinksDataSingleton.getInstance();
        }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        executorService.execute(new Runnable() {
            Elements links;
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("http://infozona.hr/kalendar").get();
                    links=doc.select("a").select(".thickboxx");
                    int i=0;
                    for(Element n:links){
                        tableEventLinks.add( new TableEventLinks(i,n.attr("href")));
                        i++;
                    }
                    linksDataSingleton.setTableEventLinks(tableEventLinks);
                    stopSelf();
                    startService(new Intent(getApplicationContext(),DownloadDataForEventsService.class));
                    executorService.shutdown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
