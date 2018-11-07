(ns bspace-part.common
  (:import java.util.concurrent.ThreadLocalRandom))

(def rand_number_gen (ThreadLocalRandom/current))
(def MAX_SEGMENT_LENGTH 30)
(def n 100)

(def WIDTH 720)
(def HEIGHT 720)
