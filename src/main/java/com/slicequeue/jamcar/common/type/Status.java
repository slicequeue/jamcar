package com.slicequeue.jamcar.common.type;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {

    TO_DO("할일"),
    ON_PROGRESS("진행 중"),
    DONE("완료");

    public final String label;

}
