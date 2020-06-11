package com.aliaksandr.rahavoi.university.shared;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Pair<L, R> {

    private L left;

    private R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }
}
