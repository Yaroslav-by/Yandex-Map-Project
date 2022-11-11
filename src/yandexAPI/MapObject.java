package yandexAPI;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapObject {

	private final static String APIKEY = Apikey.getAPIKEY();
	private final static String TYPE_GEO = "type=geo";
	private final static String TYPE_BIZ = "type=biz";
	private final static String LANG = "ru_RU";
	
	private String coordinates;
	private String name;
	private String description;
	
	MapObject(String coordinates, String name, String description) {
		this.coordinates = coordinates;
		this.name = name;
		this.description = description;
	}
	
	public String getCoordinates() {
		return coordinates;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public static String getResults(String query, boolean isGEO) {
		
		String queryType;
		if (isGEO)
			queryType = TYPE_GEO;
		else 
			queryType = TYPE_BIZ;
				
		try {
			
			HttpRequest getRequest = HttpRequest.newBuilder()
					.uri(new URI("https://search-maps.yandex.ru/v1/"
							+ "?text=" + query
							+ "&" + queryType
							+ "&lang=" + LANG
							+ "&apikey=" + APIKEY))
					.GET()
					.build();
			
			HttpClient client = HttpClient.newHttpClient();
			
			HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
			
			return getResponse.body();
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public static ArrayList<MapObject> getMapObjects(String jsonGEO, String jsonBIZ) {
		
		ArrayList<MapObject> mapObjects = new ArrayList<MapObject>();
		
		if (jsonGEO.equals(null) && jsonBIZ.equals(null)) {
			return mapObjects;
		}
		
		JSONObject jsonObject = new JSONObject(jsonGEO);
		JSONArray jsonArray = (JSONArray) jsonObject.get("features");
		
		for (Object i : jsonArray) {
			
			String coordinates = (String) ((JSONObject) i).getJSONObject("geometry")
														  .get("coordinates")
														  .toString();
			coordinates = coordinates.substring(1, coordinates.length() - 1);
			
			String name = (String) ((JSONObject) i).getJSONObject("properties").get("name");
			
			String description = "Название: " + name + "\n";
			description = description + "Адрес: "  
						+ (String) ((JSONObject) i).getJSONObject("properties").get("description") + "\n";
			description = description + "Координаты: " + coordinates;
			
			MapObject temp = new MapObject(coordinates, name, description);
			mapObjects.add(temp);
		}
		
		jsonObject = new JSONObject(jsonBIZ);
		jsonArray = (JSONArray) jsonObject.getJSONArray("features");
		
		for (Object i : jsonArray) {
			
			String coordinates = (String) ((JSONObject) i).getJSONObject("geometry")
														  .get("coordinates")
														  .toString();
			coordinates = coordinates.substring(1, coordinates.length() - 1);
			
			String name = (String) ((JSONObject) i).getJSONObject("properties").getString("name") + " (Организация)";
			
			String description = "Название: " + (String) ((JSONObject) i).getJSONObject("properties").getString("name") + "\n";
			
			//Записываем адрес в описание
			try {
				description = description + "Адрес: " + (String) ((JSONObject) i).getJSONObject("properties")
																				 .getJSONObject("CompanyMetaData")
																				 .get("address") + "\n";
			} catch (JSONException e) {
				description = description + "Адрес: не определен\n";
			}
			
			//Записываем номера телефонов в описание 
			try {
				JSONArray phonesArray = ((JSONObject) i).getJSONObject("properties")
													    .getJSONObject("CompanyMetaData")
													    .getJSONArray("Phones");
				description = description + "Моб. телефон: ";
				for (Object j : phonesArray) {
					description = description + (String) ((JSONObject) j).get("formatted") + "; ";
				}
				description = description + "\n";
			} catch (JSONException e) {
				description = description + "Моб. телефон: не определен" + "\n";
			}
			
			//Записываем время работы в описание
			try {
				description = description + "Время работы: " + (String) ((JSONObject) i).getJSONObject("properties")
																						.getJSONObject("CompanyMetaData")
					        															.getJSONObject("Hours") 
					        															.get("text") + "\n";
			} catch (JSONException e) {
				description = description + "Время работы: не определено" + "\n";
			}
			
			//Записываем категории в описание
			try {
				JSONArray categoryArray = ((JSONObject) i).getJSONObject("properties")
													    .getJSONObject("CompanyMetaData")
													    .getJSONArray("Categories");
				description = description + "Категории: ";
				for (Object j : categoryArray) {
					description = description + (String) ((JSONObject) j).get("name") + "; ";
				}
				description = description + "\n";
			} catch (JSONException e) {
				description = description + "Категории: не определены" + "\n";
			}

			description = description + "Координаты: " + coordinates;
			
			MapObject temp = new MapObject(coordinates, name, description);
			mapObjects.add(temp);
			
		}
		
		return mapObjects;
		
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
