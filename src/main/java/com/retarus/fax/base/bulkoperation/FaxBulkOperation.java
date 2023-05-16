package com.retarus.fax.base.bulkoperation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.retarus.fax.base.common.HttpMethod;
import com.retarus.fax.exception.ApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author thiagon
 * <p>
 * Class for the bulk operation request.
 * This object allows specifying a list of jobIds and an action to be performed on them.
 * The action can be either DELETE or GET.
 * <p>
 * DELETE: Bulk delete the requested jobs
 * GET: Bulk get the requested jobs
 * <p>
 * The response will be a list of FaxStatusReport objects if the action is GET.
 * The response will be a list of FaxDeletionReport objects if the action is DELETE.
 */
public class FaxBulkOperation {

    /**
     * The action to be performed on the list of jobs.
     * The action can be either DELETE or GET.
     */
    @JsonProperty("action")
    private final HttpMethod action;

    /**
     * The list of Job IDs to be processed in bulk.
     */
    @JsonProperty("jobIds")
    private final List<String> jobIds;

    /**
     * Constructor for the FaxBulkOperation class.
     *
     * @param action The action to be performed on the list of jobs.
     *               The action can be either DELETE or GET.
     * @param jobIds Sequence of Job IDs to be processed in bulk.
     */
    private FaxBulkOperation(HttpMethod action, List<String> jobIds) {
        this.action = action;
        this.jobIds = jobIds;
    }

    public static FaxBulkOperationBuilder builder() {
        return new FaxBulkOperationBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaxBulkOperation that = (FaxBulkOperation) o;
        return action == that.action && Objects.equals(jobIds, that.jobIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, jobIds);
    }

    public HttpMethod getAction() {
        return action;
    }

    public List<String> getJobIds() {
        return jobIds;
    }

    public static class FaxBulkOperationBuilder {
        private HttpMethod action;
        private List<String> jobIds;

        private FaxBulkOperationBuilder() {
        }

        /**
         * Sets the action to be performed on the list of jobs.
         *
         * @param action The action to be performed on the list of jobs.
         *               The action can be either DELETE or GET.
         * @return The FaxBulkOperationBuilder object.
         * @throws ApiException If the action is not DELETE or GET.
         */
        public FaxBulkOperationBuilder action(HttpMethod action) {

            if (action != HttpMethod.DELETE && action != HttpMethod.GET) {
                throw new ApiException("The action must be either DELETE or GET");
            }
            this.action = action;
            return this;
        }

        /**
         * Sets the list of Job IDs to be processed in bulk.
         *
         * @param jobIds List of Job IDs to be processed in bulk.
         * @return The FaxBulkOperationBuilder object.
         */
        public FaxBulkOperationBuilder jobIds(List<String> jobIds) {

            if (jobIds == null) {
                return this;
            }

            if (this.jobIds == null) {
                this.jobIds = new ArrayList<>();
            }
            this.jobIds.addAll(jobIds);
            return this;
        }

        /**
         * Sets the list of Job IDs to be processed in bulk.
         *
         * @param jobIds List of Job IDs to be processed in bulk.
         * @return The FaxBulkOperationBuilder object.
         */
        public FaxBulkOperationBuilder jobIds(String... jobIds) {
            if (jobIds == null) {
                return this;
            }

            if (this.jobIds == null) {
                this.jobIds = new ArrayList<>();
            }
            this.jobIds.addAll(Arrays.asList(jobIds));
            return this;
        }


        /**
         * Builds the FaxBulkOperation object.
         *
         * @return The FaxBulkOperation object.
         * @throws ApiException If the customer number is null or empty.
         */
        public FaxBulkOperation build() {
            return new FaxBulkOperation(action, jobIds);
        }

        public String toString() {
            return "FaxBulkOperation.FaxBulkOperationBuilder(Action=" + this.action + ", jobIds=" + this.jobIds + ")";
        }
    }
}
