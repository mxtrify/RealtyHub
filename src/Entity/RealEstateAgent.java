package Entity;

import Database.DBConn;
import java.sql.*;
import java.util.ArrayList;

public class RealEstateAgent {
    private Connection conn;
    private int ratingId;
    private int reviewId;
    private int reviewType;
    private int agentId;
    private UserAccount reviewer;
    private UserAccount agent;
    private UserProfile reviewerType;
    private byte rating;
    private String review;

    // Default constructor: Initializes a new user account with empty or default values.
    public RealEstateAgent() {
        this.ratingId = 0;
        this.reviewer = new UserAccount();
        this.agent = new UserAccount();
        this.reviewerType = new UserProfile();
        this.rating = 0;
    }

    // Extended parameterized constructor
    public RealEstateAgent(UserAccount reviewer, UserProfile reviewerType, byte rating) {
        this.reviewer = reviewer;
        this.reviewerType = reviewerType;
        this.rating = rating;
    }

    // Extended parameterized constructor
    public RealEstateAgent(UserAccount reviewer, UserProfile reviewerType, String review) {
        this.reviewer = reviewer;
        this.reviewerType = reviewerType;
        this.review = review;
    }

    public RealEstateAgent(int agentId) {
        this.agentId = agentId;
    }

    public RealEstateAgent(int reviewId, int reviewType, int agentId, byte rate, String review) {
        this.reviewId = reviewId;
        this.reviewType = reviewType;
        this.agentId = agentId;
        this.rating = rate;
        this.review = review;
    }

    // Getters
    public int getReviewId() { return reviewId; }
    public int getReviewType() { return reviewType; }
    public int getAgentId() { return agentId; }
    public UserAccount getReviewer() { return reviewer; }
    public UserAccount getAgent() { return agent; }
    public UserProfile getReviewerType() { return reviewerType; }
    public byte getRating() { return rating; }
    public String getReview() { return review; }

    // Insert User Rating and User Review into the rating and review table
    public boolean insertRateReview(RealEstateAgent newRateReview) {
        String insertRatingSQL = "INSERT INTO agent_ratings (reviewerID, reviewerType, agentId, rating) VALUES (?, ?, ?, ?)";
        String insertReviewSQL = "INSERT INTO agent_reviews (reviewerID, reviewerType, agentId, review) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ratingStmt = null;
        PreparedStatement reviewStmt = null;
        try {
            conn = new DBConn().getConnection();
            conn.setAutoCommit(false);  // Start transaction

            // Prepare and execute the first statement for ratings
            ratingStmt = conn.prepareStatement(insertRatingSQL);
            ratingStmt.setInt(1, newRateReview.getReviewId());
            ratingStmt.setInt(2, newRateReview.getReviewType());
            ratingStmt.setInt(3, newRateReview.getAgentId());
            ratingStmt.setInt(4, newRateReview.getRating());
            ratingStmt.executeUpdate();

            // Prepare and execute the second statement for reviews
            reviewStmt = conn.prepareStatement(insertReviewSQL);
            reviewStmt.setInt(1, newRateReview.getReviewId());
            reviewStmt.setInt(2, newRateReview.getReviewType());
            reviewStmt.setInt(3, newRateReview.getAgentId());
            reviewStmt.setString(4, newRateReview.getReview());
            reviewStmt.executeUpdate();

            // Commit the transaction
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            // Close statements and connection
            if (ratingStmt != null) {
                try { ratingStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (reviewStmt != null) {
                try { reviewStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            closeConnection();
        }
    }


    // Retrieves ratings from the database for this agent and returns them
    public ArrayList<RealEstateAgent> fetchAgentRatings(int agentID) {
        ArrayList<RealEstateAgent> ratings = new ArrayList<>();
        String query = "SELECT reviewerID, profileType, rating FROM agent_ratings INNER JOIN user_profile ON agent_ratings.reviewerType = user_profile.profileID WHERE agentID = ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, agentID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int reviewerID = resultSet.getInt("reviewerID");
                String profileType = resultSet.getString("profileType");
                byte rating = resultSet.getByte("rating");

                UserAccount reviewer = new UserAccount();
                reviewer.setAccountID(reviewerID);

                UserProfile ReviewerType = new UserProfile(profileType);

                RealEstateAgent agentRating = new RealEstateAgent(reviewer, ReviewerType, rating);
                ratings.add(agentRating);
            }
            return ratings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation
        }
    }

    // Retrieves ratings from the database for this agent and returns them
    public ArrayList<RealEstateAgent> fetchAgentReviews(int agentID) {
        ArrayList<RealEstateAgent> reviews = new ArrayList<>();
        String query = "SELECT reviewerID, profileType, review FROM agent_reviews INNER JOIN user_profile ON agent_reviews.reviewerType = user_profile.profileID WHERE agentID = ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, agentID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int reviewerID = resultSet.getInt("reviewerID");
                String profileType = resultSet.getString("profileType");
                String review = resultSet.getString("review");

                UserAccount reviewer = new UserAccount();
                reviewer.setAccountID(reviewerID);

                UserProfile ReviewerType = new UserProfile(profileType);

                RealEstateAgent agentReview = new RealEstateAgent(reviewer, ReviewerType, review);
                reviews.add(agentReview);
            }
            return reviews;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation
        }
    }

    // Closes the database connection to release resources
    private void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}