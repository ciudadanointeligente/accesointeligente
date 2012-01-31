/**
 * Acceso Inteligente
 *
 * Copyright (C) 2010-2011 Fundación Ciudadano Inteligente
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.accesointeligente.model.external;

import com.google.gson.annotations.SerializedName;

public class SolrResponse {
    private ResponseHeader responseHeader;
    private Response response;

    class ResponseHeader {
        private Integer status;
        private Integer QTime;
        private Params params;

        class Params {
            @SerializedName("q") private String query;
            @SerializedName("fl") private String fieldList;
            @SerializedName("qf") private String queryFields;
            @SerializedName("wt") private String responseFormat;
            private Integer start;
            private Integer rows;

			public String getQuery() {
				return query;
			}

			public void setQuery(String query) {
				this.query = query;
			}

			public String getFieldList() {
				return fieldList;
			}

			public void setFieldList(String fieldList) {
				this.fieldList = fieldList;
			}

			public String getQueryFields() {
				return queryFields;
			}

			public void setQueryFields(String queryFields) {
				this.queryFields = queryFields;
			}

			public String getResponseFormat() {
				return responseFormat;
			}

			public void setResponseFormat(String responseFormat) {
				this.responseFormat = responseFormat;
			}

			public Integer getStart() {
				return start;
			}

			public void setStart(Integer start) {
				this.start = start;
			}

			public Integer getRows() {
				return rows;
			}

			public void setRows(Integer rows) {
				this.rows = rows;
			}
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getQTime() {
            return QTime;
        }

        public void setQTime(Integer qTime) {
            QTime = qTime;
        }

        public Params getParams() {
            return params;
        }

        public void setParams(Params params) {
            this.params = params;
        }
    }

    public class Response {
    	private Integer numFound;
    	private Integer start;
    	private Document[] docs;

    	public class Document {
    		private Integer id;

			public Integer getId() {
				return id;
			}

			public void setId(Integer id) {
				this.id = id;
			}
    	}

		public Integer getNumFound() {
			return numFound;
		}

		public void setNumFound(Integer numFound) {
			this.numFound = numFound;
		}

		public Integer getStart() {
			return start;
		}

		public void setStart(Integer start) {
			this.start = start;
		}

		public Document[] getDocs() {
			return docs;
		}

		public void setDocs(Document[] docs) {
			this.docs = docs;
		}
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
