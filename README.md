# EuropePMCLinks
Repository for code to create the [Europe PMC external links](http://europepmc.org/LabsLink).

The idea is to create links from Europe PMC to WikiPathways for articles cited in our pathways. The links are
provided to Europe PMC as a combination of two XML files, one with a profile ([profile.xml](profile.xml)) and
the other a link set. This one can be generated from the RDF, which already takes care of detection of (some)
faulty PubMed identifiers.

## Generation

Step 1 is the listing of all PubMed identifiers and associated pathways from the Analysis and Reactome
collections:

```shell
curl -H "Accept: text/csv" --data-urlencode query@query.rq -G http://sparql.wikipathways.org/ -o pmid2wpid.csv
```

Step 2 is the generation of the links XML (using Groovy and Java8):

```shell
groovy makeLinkXML.groovy > links.xml
```

Step 3 is the validation of the links XML:

```shell
curl http://europepmc.org/docs/labslink.xsd -o labslink.xsd
xmllint --noout --schema labslink.xsd links.xml
```
