To install AccesoInteligente Solr compatibility a new Solr core is needed.
We provide the configuration files for that core. All you need to do is to copy the files and make the setup in Solr.

As you can see in the solr.xml file we provide there's a entry for accesointeligente:

<core name="accesointeligente" instanceDir="./accesointeligente" />

With this entry we configure the core to use the configuration files available in the folder accesointeligente/config.
We used Solr default configuration files as a base to create the ones for AccesoInteligente.

The required libraries are provided with the core.

To learn how to install solr, please refer to Solr Wiki.
We'll give you some useful links to the wiki articles used to make the configuration for AccesoInteligente:
- http://lucene.apache.org/solr/tutorial.html
- http://wiki.apache.org/solr/SolrTomcat#Solr_with_Apache_Tomcat
- http://wiki.apache.org/solr/DataImportHandler
