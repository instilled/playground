(ns instilled.playground.spring.bean.injectable
  (:import
    [instilled.playground.spring.bean
     IBean
     AwesomeBean]
    [org.springframework.stereotype
     Component]
    [org.springframework.beans.factory.annotation
     Autowired]))

(defrecord
  ^{Component true}
  TestType
  []
  #_[^{:tag AwesomeBean
     Autowired true}
   awesome-bean]

  IBean
  (doSomething [this obj]
    (println "---------------------------")
    (println "hello: " obj)
    (println "---------------------------")))
