(ns bspace-part.common
  (:import java.util.concurrent.ThreadLocalRandom))

(def rand_number_gen (ThreadLocalRandom/current))
(def MAX_SEGMENT_LENGTH 50)
(def n 1000)

;; Canvas size
(def WIDTH 720)
(def HEIGHT 720)
