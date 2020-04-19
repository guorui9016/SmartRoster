package com.gr.smartroster.callback;

import com.gr.smartroster.model.Roster;
import java.util.List;

public interface IRosterCallbackLister {
    void onRosterLoadSuccessful(List<Roster> rosterList);
    void onRosterLoadFailed(String message);
}
