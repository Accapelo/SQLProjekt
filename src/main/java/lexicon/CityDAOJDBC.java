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
        City city;
        try(Connection connection = DriverManager.getConnection(connect,user,pass);
            PreparedStatement statement = connection.prepareStatement("select * from city where ID=?");
        )
        {
            statement.setString(1,""+id);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                return city= new City(resultSet.getInt("ID"),
                        resultSet.getString("Name"),
                        resultSet.getString("CountryCode"),
                        resultSet.getString("District"),
                        resultSet.getInt("Population"));
            }



        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;

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

            return list;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
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

            return list;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
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

            return list;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
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
            return city;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public City update(City city) {
        String cityName = city.getName();

        try(Connection connection = DriverManager.getConnection(connect,user,pass);
            PreparedStatement statement = connection.prepareStatement("? city ? ?");
            Scanner scan = new Scanner(System.in);
        )
        {
            while(true){
                System.out.println("Add (1) or delete (2) city:");

                String addOrDelete=scan.nextLine();

                if(addOrDelete.equalsIgnoreCase("2")){
                    statement.setString(1,"DELETE FROM");
                    statement.setString(2,"WHERE Name LIKE ");
                    statement.setString(3,cityName);
                    break;
                }else if(addOrDelete.equalsIgnoreCase("1")){
                    statement.setString(1,"INSERT INTO");
                    statement.setString(2,"VALUES");
                    statement.setString(3,"("+city.getId()+","+city.getName()+","+city.getCode()+","+city.getDistrict()+","+city.getPopulation()+")");
                    break;
                }else{
                    System.out.println("Incorrect input");
                }
            }
            
            statement.executeUpdate();

            return city;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int delete(City city) {
        String cityName = city.getName();

        try(Connection connection = DriverManager.getConnection(connect,user,pass);
            PreparedStatement statement = connection.prepareStatement("delete from city where Name like ?");
        )
        {
            statement.setString(1,cityName);

            int row = statement.executeUpdate();

            return row;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
}
