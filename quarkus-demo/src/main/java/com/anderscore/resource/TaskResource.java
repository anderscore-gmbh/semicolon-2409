package com.anderscore.quarkus.resource;

import com.anderscore.quarkus.entity.Task;
import com.anderscore.quarkus.repository.TaskRepository;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("api/v1/tasks")
public class TaskResource {

    @Inject
    TaskRepository repository;

    @GET
    @Produces(APPLICATION_JSON)
    @Timed(name = "TaskResource.findAll",
            description = "Metric to show how long comprehensive task loading takes.",
            absolute = true)
    public List<Task> findAll() {
        return repository.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    @Metered(name = "findTaskById",
            description = "Metric to show how long task loading takes.",
            absolute = true
    )
    public Task findById(@PathParam("id") Long id) {
        Task task = repository.findById(id);

        if (task != null) {
            return task;
        }

        throw new NotFoundException();
    }

    @PUT
    @Path("/{id}")
    @Consumes(APPLICATION_JSON)
    public void update(@PathParam("id") Long id, Task task) {
        repository.update(task);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Counted(name = "tasksCreated",
            absolute = true,
            displayName = "Created tasks",
            description = "Metric to show how many tasks have been created so far.")
    public Response create(Task task, @Context UriInfo uriInfo) {
        repository.store(task);

        URI createdUri = uriInfo
                .getAbsolutePathBuilder()
                .path(task.getId().toString())
                .build();

        return Response.created(createdUri).build();
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        Task task = repository.findById(id);
        repository.delete(task);
    }
}
