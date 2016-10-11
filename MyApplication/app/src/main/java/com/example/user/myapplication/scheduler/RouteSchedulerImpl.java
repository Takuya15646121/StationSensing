package com.example.user.myapplication.scheduler;

import com.example.user.myapplication.routeextraction.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by CARROT on 2016/10/11.
 */
public class RouteSchedulerImpl implements RouteScheduler{

    private List<Station> route;

    public RouteSchedulerImpl(Station[] routeInArray){
        this.route = new ArrayList<>(Arrays.asList(routeInArray));
    }

    @Override
    public long calcRemainTimeMillis(int trainType) {
        return 0;
    }
}
