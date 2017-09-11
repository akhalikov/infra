#!/bin/sh

createdb 'backend'
createuser -P 'backend'

psql -U 'backend' -h localhost < ./create-tables.sql
