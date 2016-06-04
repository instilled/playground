(ns instilled.playground.spring.bean.clojure
  (:import
    [instilled.playground.spring.bean
     IBean
     AwesomeBean]
    [org.springframework.stereotype
     Component]
    [org.springframework.beans.factory.annotation
     Autowired]))

;; ----------------------------------------
;; defrecord

(defrecord
  ^{Component true}
  ClojureBean1
  []

  IBean
  (doSomething [this obj]
    (println "---------------------------")
    (println "FROM CLOJURE: Java invoked doSomething: " obj)
    (println "---------------------------")))


;; ----------------------------------------
;; with gen-class

(gen-class
  :name
  ^{Component true}
  instilled.playground.spring.bean.ClojureBean2

  :state
  state

  :init
  init

  :prefix
  "-"

  :constructors
  {^{Autowired true} [instilled.playground.spring.bean.AwesomeBean] []}

  :main
  false)

(defn -init
  [obj]
  "store our fields as a hash"
  [[]
   (do
     (println "---------------------------")
     (println "FROM CLOJURE: autowired constructor: " obj)
     (println "---------------------------"))])

;; -----------------------------------------------
;;
;; only definterface works here, defprotocol not supported
;; or, of course, an interface defined in java
(definterface IProto
  (setDep [^instilled.playground.spring.bean.ClojureBean2 obj]))

(defrecord
  ^{Component true}
  ClojureBean3
  []

  IProto
  (^{Autowired true} setDep [this obj]
   (println "---------------------------")
   (println "FROM CLOJURE: autowired setter another ClojureBean2: " obj)
   (println "---------------------------")))
