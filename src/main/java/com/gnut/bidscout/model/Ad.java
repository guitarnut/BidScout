package com.gnut.bidscout.model;

import java.util.ArrayList;
import java.util.List;

public interface Ad {

    String getName();

    String getCreativeId();

    int getWidth();

    int getHeight();

    List<String> getAdDomain();

    List<String> getIabCategories();

    List<Integer> getAttr();

    List<String> getMimes();

    String getAdId();

    String getCrid();
}
