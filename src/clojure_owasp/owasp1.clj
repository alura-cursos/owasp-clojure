(ns clojure_owasp.owasp1)

(use '[clojure.java.shell :only [sh]])

(defn run-cluster [config-file]
  (let [command (str "/bin/kafka " config-file)]
    (println command)
    (sh "bash" "-c" command)))

(defn run-cluster [config-file]
  (sh "/bin/kafka" config-file))

;(run-cluster "server.properties")
;(run-cluster "server.properties; ls /")

; outro exemplo equivalente
; https://github.com/nubank/clj-owasp/tree/master/examples/A1-injection

; vulnerabilidade
(defn login [username password]
  (let [sql (str "select * from Users where username='" username "' and password='" password "'")]
    (println sql)
    ; executaria o sql
    ))

(login "guilherme" "senha")
(login "guilherme" "' or id=1 having '1'='1")
(login "guilherme" "' or admin=1 having '1'='1")
