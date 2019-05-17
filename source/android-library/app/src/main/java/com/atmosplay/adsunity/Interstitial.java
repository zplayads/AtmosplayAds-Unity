/*
 * Copyright (C) 2016 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.atmosplay.adsunity;

import android.app.Activity;

import com.atmosplayads.AtmosplayAdListener;
import com.atmosplayads.AtmosplayInterstitial;


public class Interstitial {
    private AtmosplayInterstitial interstitial;
    private Activity activity;
    private UnityInterstitialAdListener adListener;

    public Interstitial(Activity activity, String appId, UnityInterstitialAdListener adListener) {
        this.activity = activity;
        this.adListener = adListener;
        interstitial = AtmosplayInterstitial.init(activity, appId);
    }

    public void loadAd(final String unitId) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                interstitial.loadAd(unitId, newRequestListener());
            }
        });
    }

    public boolean isLoaded(final String unitId) {
        interstitial.isReady(unitId);
        return true;
    }

    public void show(final String unitId) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (interstitial.isReady(unitId)) {
                    interstitial.show(unitId);
                }
            }
        });
    }

    public void setAutoloadNext(final boolean autoLoad) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                interstitial.setAutoload(autoLoad);
            }
        });
    }

    public void setChannelId(final String channelId) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                interstitial.setChannelId(channelId);
            }
        });
    }

    private AtmosplayAdListener newRequestListener() {

        return new AtmosplayAdListener() {
            @Override
            public void onAdLoaded() {
                if (adListener != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (adListener != null) {
                                adListener.onAdLoaded();
                            }
                        }
                    }).start();
                }
            }

            @Override
            public void onAdFailedToLoad(int i, final String s) {
                if (adListener != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (adListener != null) {
                                adListener.onAdFailed(s);
                            }
                        }
                    }).start();
                }
            }

            @Override
            public void onAdFailedToShow(int i, final String s) {
            }

            @Override
            public void onVideoStart() {
                if (adListener != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (adListener != null) {
                                adListener.onAdStarted();
                            }
                        }
                    }).start();
                }
            }

            @Override
            public void onVideoCompleted() {
                if (adListener != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (adListener != null) {
                                adListener.onAdVideoCompleted();
                            }
                        }
                    }).start();
                }
            }

            @Override
            public void onRewarded() {
            }

            @Override
            public void onClicked() {
                if (adListener != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (adListener != null) {
                                adListener.onAdClicked();
                            }
                        }
                    }).start();
                }
            }

            @Override
            public void onAdClosed() {
                if (adListener != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (adListener != null) {
                                adListener.onAdCompleted();
                            }
                        }
                    }).start();
                }
            }

        };
    }
}