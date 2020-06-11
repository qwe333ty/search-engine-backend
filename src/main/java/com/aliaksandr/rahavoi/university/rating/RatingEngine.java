package com.aliaksandr.rahavoi.university.rating;

import com.aliaksandr.rahavoi.university.shared.Pair;

public interface RatingEngine {

    /**
     * Начальные значения для очков и кол-ва голосов.
     *
     * Левое значение - кол-во очков.
     * Право значение - кол-во голосов.
     */
    default Pair<Float, Long> initialScoresAndVotes() {
        return new Pair<>(0.0F, 0L);
    }

    /**
     * Начальное значение для рейтинга.
     */
    default Float initialRating() {
        return 0.0F;
    }

    /**
     * Минимальные и максимальные значения по умолчанию.
     *
     * Левое значение - минимальное число.
     * Правое значение - максимальное число.
     */
    default Pair<Float, Float> minMaxRatingValues() {
        return new Pair<>(0.0F, 10.0F);
    }

    /**
     * Расчет рейтинга при помощи очков и кол-ва голосов.
     * Значение будет лежать в рамках min-max значений.
     *
     * {@link com.aliaksandr.rahavoi.university.rating.RatingEngine#minMaxRatingValues()}
     */
    Float calculateRating(Float scores, Long votes);

    /**
     * Пересчет значений на основе уже известных, а также
     * с учетом новогой оценки пользователя.
     */
    Pair<Float, Long> recalculateRating(Float scores, Long votes, Float userEstimation);
}
