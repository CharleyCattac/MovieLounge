package com.lobach.movielounge.command.impl;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.command.LocaleType;
import com.lobach.movielounge.exception.ServiceException;
import com.lobach.movielounge.util.PropertyManager;
import com.lobach.movielounge.model.Review;
import com.lobach.movielounge.service.MovieEventService;
import com.lobach.movielounge.service.ReviewService;
import com.lobach.movielounge.service.UserService;
import com.lobach.movielounge.service.impl.MovieEventServiceImpl;
import com.lobach.movielounge.service.impl.ReviewServiceImpl;
import com.lobach.movielounge.service.impl.UserServiceImpl;
import com.lobach.movielounge.servlet.RequestContent;
import com.lobach.movielounge.util.Router;

import java.util.List;

public class ShowReviewsCommand implements ActionCommand {

    private static final String BUNDLE_CONFIG = "config";
    private static final String BUNDLE_MESSAGE = "message";

    private static final String PROPERTY_REVIEWS = "path.reviews";
    private static final String PROPERTY_ERROR_MESSAGE = "error.default";
    private static final String PROPERTY_EMPTY_LIST_MESSAGE = "error.empty_list";

    private static final String ATTRIBUTE_REVIEWS = "reviews";
    private static final String ATTRIBUTE_REVIEWS_SIZE = "reviewsSize";
    private static final String ATTRIBUTE_FATAL_MESSAGE = "fatalMessage";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";

    private ReviewService reviewService;
    private UserService userService;
    private MovieEventService eventService;

    public ShowReviewsCommand() {
        reviewService = new ReviewServiceImpl();
        userService = new UserServiceImpl();
        eventService = new MovieEventServiceImpl();
    }

    @Override
    public Router execute(RequestContent content) {
        String page = PropertyManager.getProperty(BUNDLE_CONFIG, PROPERTY_REVIEWS);
        LocaleType localeType = defineLocale(content);
        List<Review> reviews;
        try {
            reviews = reviewService.findAllReviews(0,0);
            if (!reviews.isEmpty()) {
                for (Review review : reviews) {
                    review.setUser(userService.findUserById(review.getUserId()));
                    review.setMovieEvent(eventService.findEventById(review.getMovieEventId()));
                }
                content.setRequestAttribute(ATTRIBUTE_REVIEWS, reviews);
                content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, null);
            } else {
                content.setRequestAttribute(ATTRIBUTE_REVIEWS, null);
                String errorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                        PROPERTY_EMPTY_LIST_MESSAGE, localeType);
                content.setRequestAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            }
            content.setRequestAttribute(ATTRIBUTE_REVIEWS_SIZE, reviews.size());
            content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, null);
        } catch (ServiceException e) {
            logger.error("Failed to retrieve movies from db: ", e);
            String defaultErrorMessage = PropertyManager.getProperty(BUNDLE_MESSAGE,
                    PROPERTY_ERROR_MESSAGE, localeType);
            content.setRequestAttribute(ATTRIBUTE_FATAL_MESSAGE, defaultErrorMessage);
        }
        return new Router(page);
    }
}
