package com.aliaksandr.rahavoi.university.rating;

import com.aliaksandr.rahavoi.university.shared.Pair;
import com.aliaksandr.rahavoi.university.shared.exception.rating.RatingAttributesIsNullException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RatingEngineImpl implements RatingEngine {

    @Override
    public Float calculateRating(Float scores, Long votes) {
        if (scores != null && votes != null) {
            Pair<Float, Long> initialValues = initialScoresAndVotes();
            // если очки ИЛИ голоса равны начальным,
            // то возвращаем начальный рейтинг
            if (initialValues.getLeft().equals(scores) ||
                    initialValues.getRight().equals(votes)) {
                return initialRating();
            }
            return scores / votes;
        }
        Map<String, Object> paramToValue = new HashMap<>();
        paramToValue.put("scores", scores);
        paramToValue.put("votes", votes);
        throw new RatingAttributesIsNullException(buildExceptionMessage(paramToValue));
    }

    @Override
    public Pair<Float, Long> recalculateRating(Float scores, Long votes, Float userEstimation) {
        if (scores != null && votes != null && userEstimation != null) {
            Float newScores = scores + userEstimation;
            Long newVotes = votes + 1L;
            return new Pair<>(newScores, newVotes);
        }
        Map<String, Object> paramToValue = new HashMap<>();
        paramToValue.put("scores", scores);
        paramToValue.put("votes", votes);
        paramToValue.put("userEstimation", userEstimation);
        throw new RatingAttributesIsNullException(buildExceptionMessage(paramToValue));
    }

    private String buildExceptionMessage(Map<String, Object> paramToValue) {
        final StringBuilder message = new StringBuilder();
        paramToValue.forEach((key, value) -> message.append(key).append('=').append(value).append(";\n"));
        return message.toString();
    }
}
