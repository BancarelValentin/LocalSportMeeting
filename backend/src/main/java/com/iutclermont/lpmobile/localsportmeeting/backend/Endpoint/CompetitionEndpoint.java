package com.iutclermont.lpmobile.localsportmeeting.backend.Endpoint;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Competition;

import java.util.ArrayList;
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
        name = "competitionApi",
        version = "v1",
        resource = "competition",
        namespace = @ApiNamespace(
                ownerDomain = "backend.localsportmeeting.lpmobile.iutclermont.com",
                ownerName = "backend.localsportmeeting.lpmobile.iutclermont.com",
                packagePath = ""
        )
)
public class CompetitionEndpoint {
    // Make sure to add this endpoint to your web.xml file if this is a web application.
    private static final Logger logger = Logger.getLogger(CompetitionEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;


    @ApiMethod(
            name = "getByCategorie",
            path = "getByCategorie/{idCategorie}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public List<Competition> getByCategorie (@Named("idCategorie")Long idCategorie) throws NotFoundException {
        List<Competition> listCompetitions = ofy().load().type(Competition.class).filter("idCategorie", idCategorie).list();
        if (listCompetitions == null || listCompetitions.size() == 0) {
            throw new NotFoundException("Could not find Competition with idCategorie: " + idCategorie);
        }
        return listCompetitions;
    }

    @ApiMethod(
            name = "get",
            path = "competition/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Competition get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Competition with ID: " + id);
        Competition competition = ofy().load().type(Competition.class).id(id).now();
        if (competition == null) {
            throw new NotFoundException("Could not find Competition with ID: " + id);
        }
        return competition;
    }

    @ApiMethod(
            name = "insert",
            path = "competition",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Competition insert(Competition competition) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that competition.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(competition).now();
        logger.info("Created Competition with ID: " + competition.getId());

        return ofy().load().entity(competition).now();
    }

    @ApiMethod(
            name = "update",
            path = "competition/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Competition update(@Named("id") Long id, Competition competition) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(competition).now();
        logger.info("Updated Competition: " + competition);
        return ofy().load().entity(competition).now();
    }

    @ApiMethod(
            name = "remove",
            path = "competition/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Competition.class).id(id).now();
        logger.info("Deleted Competition with ID: " + id);
    }

    @ApiMethod(
            name = "list",
            path = "competition",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Competition> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Competition> query = ofy().load().type(Competition.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Competition> queryIterator = query.iterator();
        List<Competition> CompetitionList = new ArrayList<Competition>(limit);
        while (queryIterator.hasNext()) {
            CompetitionList.add(queryIterator.next());
        }
        return CollectionResponse.<Competition>builder().setItems(CompetitionList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Competition.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Competition with ID: " + id);
        }
    }
}