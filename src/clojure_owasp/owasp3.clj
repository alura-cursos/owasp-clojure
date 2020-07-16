(ns clojure_owasp.owasp3
  (:require [crypto.password.bcrypt :as password]))

(def database (atom {}))
(defn add [table document]
  (swap! database update-in [table] conj document))

(def encrypted-password (password/encrypt "banana"))
(println encrypted-password)

(println
  (password/check "banana" encrypted-password))


(defn read-file [filename]
  (-> filename
      slurp
      clojure.string/split-lines))

(def common-passwords (read-file "src/common-passwords.txt"))
(println common-passwords)

(defn is-common? [password]
  (some #(= password %) common-passwords))

(println (is-common? "senha") (is-common? "r42hu9fen3uicnru34"))






(defn register-new-user! [username password]
  (if (is-common? password)
    (throw (Exception. "Senha muito simples"))
    (let [encrypted (password/encrypt password)]
      ;      (println username password)
      (add :users {:username username, :password encrypted}))))


(println
  (register-new-user! "matheus.bernardes" "banana"))
(println
  (register-new-user! "guilherme.silveira" "senha798hwfehu9f34"))






(defn continue [chain path parameters]
  (if chain
    (let [next-one (first chain)]
      (next-one (rest chain) path parameters))))

(defn execution-layer [chain path parameters]
  (println "Executing for path" path))

(defn do-upload [parameters]
  (println "dealing with upload"))

(defn upload-layer [chain path parameters]
  (if (:upload-file parameters)
    (do-upload parameters))
  (continue chain path parameters))

(defn log-layer [chain path parameters]
  ; esse log eh perigoso... por causa dos parametros
  (println path parameters)
  (continue chain path parameters))

(defn service [path parameters]
  (let [chain [log-layer upload-layer execution-layer]]
    (continue chain path parameters)))






(service "/upload" {:uploaded-file "hi.txt"})
(service "/login" {:password "password"})

