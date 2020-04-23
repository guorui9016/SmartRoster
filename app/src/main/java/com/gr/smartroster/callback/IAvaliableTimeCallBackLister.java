package com.gr.smartroster.callback;

import com.gr.smartroster.model.AvaliableTime;
import java.util.List;

public interface IAvaliableTimeCallBackLister {
    void OnAvaliableTimeSuccessful(List<AvaliableTime> avaliableTimeList);

    void OnAvaliableTimeLoadFailed(String message);
}
