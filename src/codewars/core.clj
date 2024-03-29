(ns codewars.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello World!"))

(defn -main
  "main"
  [& args]
  (println "hello codewars"))

(defn power [base exponent]
  (if (zero? exponent)
    1
    (loop [cnt exponent
           acc 1]
      (if (zero? cnt)
        acc
        (recur (dec cnt) (*' acc base))))))

(defn row-sum-odd-numbers [row-num]
  ;; your code here
  (power row-num 3))

(defn thirt [n]
  ;; your code here
  (loop [nr n]
    (let [tmp (->> nr
                   pr-str
                   (map #(- (int %) 48))
                   reverse
                   (map * (cycle [1 10 9 12 3 4]))
                   (reduce +))]
      (if (= tmp nr)
        tmp
        (recur tmp)))))

(defn stock-list [list-of-books list-of-cat]
  ;; your code
  (if (every? seq [list-of-books list-of-cat])
    (let [m (->> list-of-cat
                 (mapv #(vector % 0))
                 flatten
                 (apply hash-map))
          rmap (reduce
                (fn [acc item]
                  (let [cat (->> item first str)
                        num (->> item (re-find #"\d+") Integer/parseInt)]
                    (if (contains? acc cat)
                      (update acc cat + num)
                      acc)))
                m
                list-of-books)]
      (map #(vector % (rmap %)) list-of-cat))
    []))

(defn evaporator [content, evap_per_day, threshold]
  (count
   (take-while
    #(> % threshold)
    (iterate #(* % (/ (- 100 evap_per_day) 100)) 100))))

(defn accum [s]
  (clojure.string/join "-"
                       (map-indexed
                        (fn [idx elm]
                          (clojure.string/capitalize
                           (apply str
                                  (repeat (inc idx) elm)))) s)))

(defn partlist [arr]
  (mapv
   #(vector (clojure.string/join " " (take % arr))
            (clojure.string/join " " (drop % arr)))
   (range 1 (count arr))))

(defn create-phone-number [nums]
  (let [[f3 rest] (split-at 3 nums)
        [f36 l4] (split-at 3 rest)]
    (apply str "(" (apply str f3) ") " (apply str f36) "-" l4)))

(defn find-odd [xs]
  (ffirst (filter (fn [[k v]] (odd? v))
                  (frequencies xs))))

(defn find-odd [xs] (reduce bit-xor xs))

(defn product-fib [prod]
  (let [fibseq (iterate (fn [[a b]] [b (+' a b)]) [1 1])]
    (first (filter some? (map (fn [[l r]]
                                (let [pd (*' l r)]
                                  (if (>= pd prod)
                                    [l r (= prod pd)]))) fibseq)))))

(defn bouncing-balls [h bounce window]
  (if (and (> h 0)
           (> bounce 0)
           (< bounce 1)
           (< window h))
    (count (take-while #(> (first %) window)
                       (iterate (fn [[hgt dir]]
                                  (if (> dir 0)
                                    [(* hgt bounce) (* -1 dir)]
                                    [hgt (* -1 dir)]))
                                [h 1])))
    -1))

(defn play-pass [s n]
  (letfn [(shift-alpha [c]
            (cond
              (Character/isLowerCase c)
              (char (+ (int \a) (mod (+ (- (int c) (int \a)) n) 26)))
              (Character/isUpperCase c)
              (char (+ (int \A) (mod (+ (- (int c) (int \A)) n) 26)))
              (Character/isDigit c)
              (char (+ (int \0) (- 9 (- (int c) (int \0)))))
              :else c))
          (upcase-even [idx elm]
            (if (even? idx)
              (clojure.string/upper-case elm)
              (clojure.string/lower-case elm)))]
    (->> s
         (map shift-alpha)
         (map-indexed upcase-even)
         reverse
         (apply str))))

(defn hamming [n]
  (letfn [(min-in-seq-fn [nr]
            (fn [seq]
              (let [dest (long (/ (last seq) nr))
                    idx (java.util.Collections/binarySearch seq dest)]
                (->> idx
                     inc
                     Math/abs
                     (nth seq)
                     (* nr)))))]
    (loop [h-seq [1] i 0]
      (if (< i n)
        (recur
         (conj h-seq
               (min
                ((min-in-seq-fn 2) h-seq)
                ((min-in-seq-fn 3) h-seq)
                ((min-in-seq-fn 5) h-seq)))
         (inc i))
        (last h-seq)))))

(defn hamming [n]
  (loop [i 0 pool (sorted-set 1)]
    (if (< i n)
      (let [m (first pool)]
        (recur (inc i) (disj
                        (apply conj pool (map (partial *' m) [2 3 5]))
                        m)))
      (first pool))))

(defn hamming [n]
  (loop [results [1]
         i 0
         exps [0 0 0]]
    (if (>= i n)
      (last results)
      (let [hams (map-indexed #(* (results (exps %)) %2) [2 3 5])
            min-val (apply min hams)
            exp-changes (map #(if (= % min-val) 1 0) hams)
            new-exps (map + exps exp-changes)]
        (println results exps hams new-exps)
        (recur (conj results min-val) (inc i) (vec new-exps))))))
