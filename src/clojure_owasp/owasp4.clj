(ns clojure_owasp.owasp4
  (:require [clojure.xml :as xml]))

(defn startparse-sax-no-doctype [s ch]
  (..
    (doto (javax.xml.parsers.SAXParserFactory/newInstance)
      (.setFeature javax.xml.XMLConstants/FEATURE_SECURE_PROCESSING true)
      (.setFeature "http://apache.org/xml/features/disallow-doctype-decl" true))
    (newSAXParser)
    (parse s ch)))

(defn parse-document [xml-document]
  (xml/parse xml-document startparse-sax-no-doctype))

(defn get-document [uri]
  (-> uri
      slurp
      (.getBytes "UTF-8")
      java.io.ByteArrayInputStream.
      parse-document))

(println (get-document "src/nasty.xml"))
