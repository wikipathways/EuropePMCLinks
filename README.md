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

Step 2 is the generation of the links XML (using Groovy and Java8), which requires passing the provider ID
as allocated by EuropePMC:

```shell
groovy makeLinkXML.groovy <providerID> > links.xml
```

Step 3 is the validation of the links XML:

```shell
curl http://europepmc.org/docs/labslink.xsd -o labslink.xsd
xmllint --noout --schema labslink.xsd links.xml
```

### Example output

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<links>
  <link providerId="-1">
    <resource>
      <title>DAP12 interactions (Homo sapiens)</title>
      <url>https://www.wikipathways.org/instance/WP2694</url>
      <image>https://www.wikipathways.org//wpi/wpi.php?action=downloadFile&amp;type=png&amp;pwTitle=Pathway:WP2694</image>
    </resource>
    <record>
      <source>MED</source>
      <id>10021361</id>
    </record>
  </link>
</links>
```

## Upload to EuropePMC

Finally, the `links.xml` and `profile.xml` files are uploaded to EuropePMC's FTP server. The credentials are available with the current
uploader. The file is placed in a hidden folder.

```shell
ncftp -u <secretuser> -p <password> something.ebi.ac.uk
cd <hiddenFolder>
```
