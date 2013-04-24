Restful TV Schedule API
=======================

##Build and deploy
Execute the following command from the project directory:

    mvn clean install

Copy the generated war file from the `target` directory of the project to the `webapps` tomcat directory.

##Calling the API
###Get all programmes

It's possible to retrieve the whole schedule with the following HTTP `GET` request:

    curl localhost:8080/metabroadcast-test02/programmes/ -v -X GET

Response example (with a 200 HTTP Status):

    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <schedule>
        <programme>
            <id>5</id>
            <name>Walking dead</name>
            <channel>bbc two</channel>
            <startTime>2013-04-15 21:30:00</startTime>
        </programme>
        <programme>
            <id>2</id>
            <name>The Office</name>
            <description>The programme is about the day-to-day lives of office employees in the Slough branch of the fictitious Wernham Hogg Paper Company</description>
            <channel>bbc two</channel>
            <startTime>2013-05-03 21:00:00</startTime>
        </programme>
        <programme>
            <id>1</id>
            <name>Big Bang Theory</name>
            <description>A woman who moves into an apartment across the hall from two brilliant but socially awkward physicists shows them how little they know about life outside of the laboratory</description>
            <channel>bbc one</channel>
            <startTime>2013-05-02 22:30:00</startTime>
        </programme>
        <programme>
            <id>3</id>
            <name>The IT Crowd</name>
            <description>The show revolves around the three staff members of its IT department</description>
            <channel>itv4</channel>
            <startTime>2013-04-26 20:30:00</startTime>
        </programme>
        <programme>
            <id>4</id>
            <name>How I Met Your Mother</name>
            <description>Ted searches for the woman of his dreams in New York City with the help of his four best friends</description>
            <channel>bbc two</channel>
            <startTime>2013-04-15 21:30:00</startTime>
        </programme>
    </schedule>

###Retrieve a single programme
It's possible to retrieve data related to a single programme (providing its `id`), with the following HTTP `GET` request:

    curl localhost:8080/metabroadcast-test02/programmes/4 -v -X GET

Where in this case 4 is the id of the programme to retrieve data for.
Response example:

    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <programme>
        <id>4</id>
        <name>How I Met Your Mother</name>
        <description>Ted searches for the woman of his dreams in New York City with the help of his four best friends</description>
        <channel>bbc two</channel>
        <startTime>2013-04-15 21:30:00</startTime>
    </programme>

###Insert a new programme

It's possible to insert a new programme with the following HTTP `POST` request:

    curl localhost:8080/metabroadcast-test02/programmes/ -v -X POST -HContent-type:application/xml -d "<programme><name>The Walking Dead</name><description>The Walking Dead description</description><channel>bbc two</channel><startTime>2013-04-26 22:30:00</startTime></programme>"

The system create a new `id` automatically and answer with the new programme data.
Response example (with a 201 HTTP Status):

    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <programme>
        <id>5</id>
        <name>The Walking Dead</name>
        <description>The Walking Dead description</description>
        <channel>bbc two</channel>
        <startTime>2013-04-26 22:30:00</startTime>
    </programme>

###Update a programme

It's possible to update an existing programme with the following HTTP `PUT` request:

    curl localhost:8080/metabroadcast-test02/programmes/5 -v -X PUT -HContent-type:application/xml -d "<programme><name>The Walking Dead Reloaded</name><description>The Walking Dead updated description</description><channel>bbc two</channel><startTime>2013-04-26 22:30:00</startTime></programme>"

No response is provided in this case (Just a 200 HTTP Status)

###Delete a programme

It's possible to delete an existing programme with the following HTTP `DELETE` request:

    curl localhost:8080/metabroadcast-test02/programmes/5 -v -X DELETE

No response is provided in this case (Just a 200 HTTP Status)

###Query by channel and startTime

It's possible to filter data providing a query URL with the following (optional) parameters:

* channel (the name of the channel of interest)
* dateFrom (the startTime of the programm must be greater than or equals the provided date)
* dateTo (the startTime of the programm must be lesser than or equals the provided date)

The accepted format of the date is `"yyyy-MM-dd HH:mm:ss"` example `"2013-04-26 12:30:00"`

Query example:

    curl 'localhost:8080/metabroadcast-test02/programmes/query.xml?channel=bbc%20one&dateFrom=2013-04-16%2012:00:00' -X GET

Response example (with a 200 HTTP Status)

    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <schedule>
        <programme>
            <id>1</id>
            <name>Big Bang Theory</name>
            <description>A woman who moves into an apartment across the hall from two brilliant but socially awkward physicists shows them how little they know about life outside of the laboratory</description>
            <channel>bbc one</channel>
            <startTime>2013-05-02 22:30:00</startTime>
        </programme>
    </schedule>

##Exception Handling

The following HTTP Status are returned in case of exceptions:

*404 (Not found)* for `GET`, `POST` or `DELETE` requests related to an `id` not existing in the data store.
An error message is also provided, example:

    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <errors>
        <error>The required resource is not present</error>
    </errors>

*400 (Bad request)* for all kind of requests for which a wrong data is sent as input data. For example if a string id is used instead of a number one.
An error message is also provided, example:

    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <errors>
        <error>The resource id should be a number</error>
    </errors>

##Input data validation

For `POST` and `PUT` requests programme data is validated before creating or updated the datastore. The following fields are required:

* name
* channel
* startTime

If one of these properties is missing in the body a 400 HTTP Status is returned with an error message explaining the problem, for example:

    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <errors>
        <error>Field <name> is required</error>
        <error>Field <channel> is required</error>
    </errors>