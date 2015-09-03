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
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Categorie;

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
        name = "categorieApi",
        version = "v1",
        resource = "categorie",
        namespace = @ApiNamespace(
                ownerDomain = "backend.localsportmeeting.lpmobile.iutclermont.com",
                ownerName = "backend.localsportmeeting.lpmobile.iutclermont.com",
                packagePath = ""
        )
)
public class CategorieEndpoint {
    // Make sure to add this endpoint to your web.xml file if this is a web application.
    private static final Logger logger = Logger.getLogger(CategorieEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    @ApiMethod(
            name = "getBySport",
            path = "categorieBySport/{idSport}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public List<Categorie> getBySport (@Named("idSport")Long idSport) throws NotFoundException {
        List<Categorie> listCategorie = ofy().load().type(Categorie.class).filter("idSport", idSport).list();
        if (listCategorie == null || listCategorie.size() == 0) {
            throw new NotFoundException("Could not find Categorie with idSport: " + idSport);
        }
        return listCategorie;
    }

    @ApiMethod(
            name = "get",
            path = "categorie/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Categorie get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Categorie with ID: " + id);
        Categorie categorie = ofy().load().type(Categorie.class).id(id).now();
        if (categorie == null) {
            throw new NotFoundException("Could not find Categorie with ID: " + id);
        }
        return categorie;
    }

    @ApiMethod(
            name = "insert",
            path = "categorie",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Categorie insert(Categorie categorie) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that categorie.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(categorie).now();
        logger.info("Created Categorie with ID: " + categorie.getId());

        return ofy().load().entity(categorie).now();
    }

    @ApiMethod(
            name = "update",
            path = "categorie/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Categorie update(@Named("id") Long id, Categorie categorie) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(categorie).now();
        logger.info("Updated Categorie: " + categorie);
        return ofy().load().entity(categorie).now();
    }

    @ApiMethod(
            name = "remove",
            path = "categorie/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Categorie.class).id(id).now();
        logger.info("Deleted Categorie with ID: " + id);
    }

    @ApiMethod(
            name = "list",
            path = "categorie",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Categorie> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Categorie> query = ofy().load().type(Categorie.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Categorie> queryIterator = query.iterator();
        List<Categorie> CategorieList = new ArrayList<Categorie>(limit);
        while (queryIterator.hasNext()) {
            CategorieList.add(queryIterator.next());
        }
        return CollectionResponse.<Categorie>builder().setItems(CategorieList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Categorie.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Categorie with ID: " + id);
        }
    }
}