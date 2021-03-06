package cesatec.cesatec.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import cesatec.cesatec.constants.ApiConstants;
import cesatec.cesatec.models.Authorization;

/**
 * Deserializer used to transform a JSON string into a Authorization object
 */
public class AuthorizationDeserializer implements JsonDeserializer<Authorization> {
    private final static String TAG = "AuthorizationDeserializer";

    @Override
    public Authorization deserialize(JsonElement json,
                                     Type typeOfT,
                                     JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        // Date when the authorization starts to be valid
        final String authorizationStart = jsonObject.get(
                ApiConstants.AuthorizationsResource.FIELD_DATE_START).getAsString();

        // Date when the authorization stops being valid
        final String authorizationEnd = jsonObject.get(
                ApiConstants.AuthorizationsResource.FIELD_DATE_END).getAsString();

        // Start of the time range when the authorization can be used by the student
        final String timeStart = jsonObject.get(
                ApiConstants.AuthorizationsResource.FIELD_TIME_START).getAsString();

        // End of the time range when the authorization can be used by the student
        final JsonElement timeEndElement = jsonObject.get(
                ApiConstants.AuthorizationsResource.FIELD_TIME_END);
        String timeEnd;
        if (timeEndElement.isJsonNull()) {
            timeEnd = null;
        } else {
            timeEnd = timeEndElement.getAsString();
        }

        // Day of the week when the authorization is valid
        final JsonElement weekdayElement = jsonObject.get(
                ApiConstants.AuthorizationsResource.FIELD_WEEKDAY);
        int weekday;
        if (weekdayElement.isJsonNull()) {
            weekday = -1;
        } else {
            // Integer representing a day of the week
            weekday = weekdayElement.getAsInt();
        }

        // Responsible that created the authorization
        final String responsible = jsonObject.get(
                ApiConstants.AuthorizationsResource.FIELD_RESPONSIBLE).getAsString();

        // Returns a new Authorization object based on the JSON data
        return new Authorization(authorizationStart, authorizationEnd,
                timeStart, timeEnd, weekday, responsible);
    }
}
