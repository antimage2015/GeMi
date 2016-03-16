package com.crazy.gemi.ui.cheaperjson;


import java.util.List;

public class CheaperInfo {

    private List<CheaperAdvertisingKey> advertisingKey;
    private List<CheaperHotkey> hotkey;

    public List<CheaperAdvertisingKey> getAdvertisingKey() {
        return advertisingKey;
    }

    public void setAdvertisingKey(List<CheaperAdvertisingKey> advertisingKey) {
        this.advertisingKey = advertisingKey;
    }

    public List<CheaperHotkey> getHotkey() {
        return hotkey;
    }

    public void setHotkey(List<CheaperHotkey> hotkey) {
        this.hotkey = hotkey;
    }
}
