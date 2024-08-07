// Copyright (c) 2019  Egon Willighagen <egon.willighagen@gmail.com>
//
// MIT License

def providerID = null

if (args.length < 1) {
  println "groovy makeLinkXML.groovy <providerID>"
  System.exit(0)
} else {
  providerID = args[0]
}

println "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
println "<links>"
def lines = new File("pmid2wpid.csv").readLines()
lines.each { String line ->
  data = line.split(",")
  pmid = data[0].replace("\"","")
  wpid = data[1].replace("\"","")
  name = data[2].replace("\"","").replace("&","&amp;")
  if (wpid == "wpid") return;
  println "  <link providerId=\"${providerID}\">"
  println "    <resource>"
  println "      <title>${name}</title>"
  println "      <url>https://www.wikipathways.org/instance/${wpid}</url>"
  println "      <image>https://upload.wikimedia.org/wikipedia/commons/3/34/Wplogo_500.png</image>"
  println "    </resource>"
  println "    <record>"
  println "      <source>MED</source>"
  println "      <id>${pmid}</id>"
  println "    </record>"
  println "  </link>"
}
println "</links>"
