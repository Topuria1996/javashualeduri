package com.btu.weather.nodartopuriaweatherbtu.davaleba1;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//http://localhost:8080/nodartopuriaweatherbtu-1.0-SNAPSHOT/api/weather
@Path("/weather")
public class WeatherResource {
    List<Weather> weatherList = new ArrayList<>();
    String[] cityNames = {"tbilisi", "batumi", "kutaisi","zugdidi"};

    public WeatherResource() {
        for (int i = 0; i < cityNames.length; i++) {
            Weather weatherObject = new Weather(cityNames[i], 23.3, 12, 13, 14, Weather.DirectionType.values()[i]);
            weatherList.add(weatherObject);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Weather> getWeather() {
        return weatherList;
    }

    @GET
    @Path("/{city}")
    @Produces(MediaType.APPLICATION_JSON)
    public Weather getWeatherCity(@PathParam("city") String cityName) {

        for (Weather x : weatherList) {
            if (x.cityName.equals(cityName.toLowerCase(Locale.ROOT))) {
                return x;
            }
        }
        return null;
    }
}