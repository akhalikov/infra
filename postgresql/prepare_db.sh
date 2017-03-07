#!/bin/sh

BACKEND_DB="backend"
BACKEND_USER="backend"

createdb ${BACKEND_DB}

createuser -P ${BACKEND_USER}

psql -U ${BACKEND_USER} -h localhost < sql/db-schema.sql
