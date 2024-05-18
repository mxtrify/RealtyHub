package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Config.DBConfig;
import Entity.AgentDetails;


public class AgentController {
    private ArrayList<AgentDetails> agents;
    private DBConfig dbConfig;

    public AgentController() {
        this.agents = new ArrayList<>();
        dbConfig = new DBConfig();
    }

    // Method to retrieve agent details from the database
    public ArrayList<AgentDetails> getAgentFromDatabase() {
        ArrayList<AgentDetails> agentFromDB = new ArrayList<>();
        try (Connection connection = dbConfig.getConnection()){
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM agent_details");
        ResultSet resultSet = statement.executeQuery();{
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    agentFromDB.add(new AgentDetails(id, name));
                }
            }
    }catch (SQLException e) {
            e.printStackTrace();
}
        return agentFromDB;
    }
    // Method to search for an agent by name
    public ArrayList<AgentDetails> searchAgentsByName(String name) {
        ArrayList<AgentDetails> searchResults = new ArrayList<>();
        for (AgentDetails agent : agents) {
            if (agent.getName().equalsIgnoreCase(name)) {
                searchResults.add(agent);
            }
        }
        return searchResults;
    }
}
