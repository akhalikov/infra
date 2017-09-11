#!/bin/sh

createdb -U postgres 'backend'
createuser -U postgres -P 'backend'

psql -U 'backend' -h localhost < ./create-tables.sql
