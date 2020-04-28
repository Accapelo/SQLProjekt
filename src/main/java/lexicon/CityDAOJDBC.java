package lexicon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CityDAOJDBC implements CityDAO {

    String connect = "jdbc:mysql://localhost:3306/world?&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Berlin";
    String user = "root";
    String pass = "root";

    public static void main( String[] args )
    {
        CityDAOJDBC c = new CityDAOJDBC();
        City a = c.findById(1);
        System.out.println(a.getName());
    }

    @Override
    public City findById(int id) {
        City city=null;
        try(Connection connection = DriverManager.getConnection(connect,user,pass);
            PreparedStatement statement = connection.prepareStatement("select * from city where ID=?");
        )
        {
            statement.setInt(1,id);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                city= new City(resultSet.getInt("ID"),
                        resultSet.getString("Name"),
                        resultSet.getString("CountryCode"),
                        resultSet.getString("District"),
                        resultSet.getInt("Population"));
            }



        }catch(SQLException e){
            e.printStackTrace();
        }

        return city;

    }

    @Override
    public List<City> findByCode(String code) {

        List<City> list = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(connect,user,pass);
            PreparedStatement statement = connection.prepareStatement("select * from city where CountryCode=?");
        )
        {
            statement.setString(1,code);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                list.add( new City(resultSet.getInt("ID"),
                        resultSet.getString("Name"),
                        resultSet.getString("CountryCode"),
                        resultSet.getString("District"),
                        resultSet.getInt("Population")));
            }



        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<City> findByName(String name) {
        List<City> list = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(connect,user,pass);
            PreparedStatement statement = connection.prepareStatement("select * from city where Name=?");
        )
        {
            statement.setString(1,name);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                list.add( new City(resultSet.getInt("ID"),
                        resultSet.getString("Name"),
                        resultSet.getString("CountryCode"),
                        resultSet.getString("District"),
                        resultSet.getInt("Population")));
            }



        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<City> findAll() {
        List<City> list = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(connect,user,pass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from city");
        )
        {
            while(resultSet.next()){
                list.add( new City(resultSet.getInt("ID"),
                        resultSet.getString("Name"),
                        resultSet.getString("CountryCode"),
                        resultSet.getString("District"),
                        resultSet.getInt("Population")));
            }



        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public City add(City city) {

        try(Connection connection = DriverManager.getConnection(connect,user,pass);
            PreparedStatement statement = connection.prepareStatement("insert into city values(?,?,?,?)");
        )
        {
            statement.setString(1,""+city.getId());
            statement.setString(2,city.getName());
            statement.setString(3,city.getCode());
            statement.setString(4,city.getDistrict());
            statement.setString(5,""+city.getPopulation());

            statement.executeUpdate();


        }catch(SQLException e){
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public City update(City city) {
        int cityID = city.getId();

        Scanner scan = new Scanner(System.in);

        System.out.println("Change value by typing \"Column=NewValue\"");

        String updateAction=scan.nextLine();


        try(Connection connection = DriverManager.getConnection(connect,user,pass);
            PreparedStatement statement = connection.prepareStatement("UPDATE city SET ? WHERE ?");

        )
        {

            statement.setString(1,updateAction);
            statement.setString(2,"ID = "+cityID);

            statement.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }

        city=findById(cityID);

        return city;
    }

    @Override
    public int delete(City city) {
        String cityName = city.getName();
        int row=-1;

        try(Connection connection = DriverManager.getConnection(connect,user,pass);
            PreparedStatement statement = connection.prepareStatement("delete from city where Name like ?");
        )
        {
            statement.setString(1,cityName);

            row = statement.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return row;
    }
}
