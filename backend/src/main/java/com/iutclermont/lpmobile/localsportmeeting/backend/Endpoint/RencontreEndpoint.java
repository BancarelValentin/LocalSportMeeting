package com.iutclermont.lpmobile.localsportmeeting.backend.Endpoint;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.repackaged.com.google.api.client.util.DateTime;
import com.googlecode.objectify.cmd.Query;
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Rencontre;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.OfyService.ofy;


/**
 * WARNING: This generated code is intended as a sample for demonstrating the usage of
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "rencontreApi",
        version = "v1",
        resource = "rencontre",
        namespace = @ApiNamespace(
                ownerDomain = "backend.localsportmeeting.lpmobile.iutclermont.com",
                ownerName = "backend.localsportmeeting.lpmobile.iutclermont.com",
                packagePath = ""
        )
)
public class RencontreEndpoint {
    // Make sure to add this endpoint to your web.xml file if this is a web application.
    private static final Logger logger = Logger.getLogger(RencontreEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    @ApiMethod (
            name = "getRencontreByCompetition",
            path = "rencontreByCompetition/{idCompetition}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public List<Rencontre> getRencontreByCompetition (@Named("idCompetition")Long idCompetition) throws NotFoundException{
        List<Rencontre> listRencontres = ofy().load().type(Rencontre.class).filter("idCompetition", idCompetition).list();
        if (listRencontres == null || listRencontres.size() == 0) {
            throw new NotFoundException("Could not find Rencontre with idCompetition: " + idCompetition);
        }
        return listRencontres;
    }

    @ApiMethod (
            name = "getRencontreBySport",
            path = "rencontreBySport/{idSport}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public List<Rencontre> getRencontreBySport (@Named("idSport")Long idSport) throws NotFoundException {
        List<Rencontre> listRencontres = ofy().load().type(Rencontre.class).filter("idSport", idSport).order("-date").list();
        if (listRencontres == null || listRencontres.size() == 0) {
            throw new NotFoundException("Could not find Rencontre with idSport: " + idSport);
        }
        return listRencontres;
    }

    @ApiMethod(
            name = "get",
            path = "rencontre/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Rencontre get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Rencontre with ID: " + id);
        Rencontre rencontre = ofy().load().type(Rencontre.class).id(id).now();
        if (rencontre == null) {
            throw new NotFoundException("Could not find Rencontre with ID: " + id);
        }
        return rencontre;
    }

    @ApiMethod(
            name = "insert",
            path = "rencontre",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Rencontre insert(Rencontre rencontre) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that rencontre.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(rencontre).now();
        logger.info("Created Rencontre with ID: " + rencontre.getId());

        return ofy().load().entity(rencontre).now();
    }


    @ApiMethod(
            name = "update",
            path = "rencontre/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Rencontre update(@Named("id") Long id, Rencontre rencontre) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(rencontre).now();
        logger.info("Updated Rencontre: " + rencontre);
        return ofy().load().entity(rencontre).now();
    }

    @ApiMethod(
            name = "remove",
            path = "rencontre/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Rencontre.class).id(id).now();
        logger.info("Deleted Rencontre with ID: " + id);
    }

    @ApiMethod(
            name = "list",
            path = "rencontre",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Rencontre> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Date currentDate = new Date();
        Query<Rencontre> query = ofy().load().type(Rencontre.class).filter("date >=", currentDate).order("-date").limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Rencontre> queryIterator = query.iterator();
        List<Rencontre> RencontreList = new ArrayList<Rencontre>(limit);
        while (queryIterator.hasNext()) {
            RencontreList.add(queryIterator.next());
        }
        return CollectionResponse.<Rencontre>builder().setItems(RencontreList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Rencontre.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Rencontre with ID: " + id);
        }
    }
}