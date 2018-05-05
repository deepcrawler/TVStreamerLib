/*
 * KeyControl
 * TVCastLib
 * 
 * Copyright (c) 2014 Hamed Ghaderipour.
 * Created by Hamed Ghaderipour on 19 Jan 2014
 * 

 */

package com.TVCastLib.service.capability;

import com.TVCastLib.service.capability.listeners.ResponseListener;

public interface KeyControl extends CapabilityMethods {
    public final static String Any = "KeyControl.Any";

    public final static String Up = "KeyControl.Up";
    public final static String Down = "KeyControl.Down";
    public final static String Left = "KeyControl.Left";
    public final static String Right = "KeyControl.Right";
    public final static String OK = "KeyControl.OK";
    public final static String Back = "KeyControl.Back";
    public final static String Home = "KeyControl.Home";
    public final static String Send_Key = "KeyControl.SendKey";
    public final static String KeyCode = "KeyControl.KeyCode";

    public enum KeyCode {
        NUM_0 (0),
        NUM_1 (1),
        NUM_2 (2),
        NUM_3 (3),
        NUM_4 (4),
        NUM_5 (5),
        NUM_6 (6),
        NUM_7 (7),
        NUM_8 (8),
        NUM_9 (9),

        DASH (10),
        ENTER (11);

        private final int code; 

        private static final KeyCode[] codes = {
            NUM_0, NUM_1, NUM_2, NUM_3, NUM_4, NUM_5, NUM_6, NUM_7, NUM_8, NUM_9, DASH, ENTER
        };

        KeyCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static KeyCode createFromInteger(int keyCode) {
            if (keyCode >= 0 && keyCode < codes.length) {
                return codes[keyCode];
            }
            return null;
        }
    }

    public final static String[] Capabilities = {
        Up,
        Down,
        Left,
        Right,
        OK,
        Back,
        Home,
        KeyCode,
    };

    public KeyControl getKeyControl();
    public CapabilityPriorityLevel getKeyControlCapabilityLevel();

    public void up(ResponseListener<Object> listener);
    public void down(ResponseListener<Object> listener);
    public void left(ResponseListener<Object> listener);
    public void right(ResponseListener<Object> listener);
    public void ok(ResponseListener<Object> listener);
    public void back(ResponseListener<Object> listener);
    public void home(ResponseListener<Object> listener);
    public void sendKeyCode(KeyCode keycode, ResponseListener<Object> listener);
}
