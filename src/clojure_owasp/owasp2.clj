(ns clojure_owasp.owasp2
  (:require [crypto.password.bcrypt :as password]))

(def database (atom {}))
(defn add [table document]
  (swap! database update-in [table] conj document))

(defn register-new-user! [username password]
  (add :users {:username username, :password password}))

;(println
;  (register-new-user! "matheus.bernardes" "banana"))

(def encrypted-password (password/encrypt "banana"))
(println encrypted-password)

(println
  (password/check "banana" encrypted-password))


(defn register-new-user! [username password]
  (let [encrypted (password/encrypt password)]
    (add :users {:username username, :password encrypted})))

;
;(println
;  (register-new-user! "matheus.bernardes" "banana"))
;(println
;  (register-new-user! "guilherme.silveira" "senha"))

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
      (add :users {:username username, :password encrypted}))))


(println
  (register-new-user! "matheus.bernardes" "banana"))
(println
  (register-new-user! "guilherme.silveira" "senha798hwfehu9f34"))





