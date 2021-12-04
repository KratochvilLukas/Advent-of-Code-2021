import org.junit.jupiter.api.Test

import java.util.stream.Collectors

class Day4 {

    @Test
    void 'day 4 - 1'() {
        def input = FileInputReader.readInputFromFile(this.getClass().getResource("/Day4.txt").toURI())
        def inputs = new Inputs(input)

        for (int i = 0; i < inputs.calledNumbers.size(); i++) {
            def winnerBoard = inputs.mark(inputs.calledNumbers.get(i), false)
                if (winnerBoard != null) {
                    println winnerBoard.calculateResult()
                    break
                }
        }
    }

    @Test
    void 'day 4 - 2'() {
        def input = FileInputReader.readInputFromFile(this.getClass().getResource("/Day4.txt").toURI())
        def inputs = new Inputs(input)

        def lastWinnerBoard = null
        for (int i = 0; i < inputs.calledNumbers.size(); i++) {
            def winnerBoard = inputs.mark(inputs.calledNumbers.get(i), true)
            if (winnerBoard != null) {
                lastWinnerBoard = winnerBoard
            }
        }
        println lastWinnerBoard.calculateResult()
    }

    private class Inputs {
        List<Integer> calledNumbers
        List<Board> boards = new ArrayList<>()

        Inputs(List<String> inputs) {
            calledNumbers = Arrays.asList(inputs.get(0).split(",")).stream().map{it -> Integer.valueOf(it)}.collect(Collectors.toList())
            for (int i = 2; i < inputs.size(); i+=6) {
                boards.add(new Board(inputs.subList(i, i + 5)))
            }
        }

        Board mark(int number, boolean cont) {
            Board winner = null
            for (int i = 0; i < boards.size(); i++) {
                if (boards.get(i).mark(number)) {
                    winner = boards.get(i)
                    if (!cont) {
                        return winner
                    }
                }
            }
            return winner
        }

    }

    private class Board {
        List<Panel> rows = new ArrayList<>()
        List<Panel> columns = new ArrayList<>()
        int lastMarked = 0
        boolean alreadyWin = false

        Board(List<String> input) {
            for (int i = 0; i < 5; i++) {
                List<Integer> numbersInRow = Arrays.asList(input.get(i).trim().replaceAll(" {2}", " ").split(" ")).stream().map{it -> Integer.valueOf(it)}.collect(Collectors.toList())
                rows.add(new Panel(numbersInRow))
            }
            List<Integer> column = new ArrayList<>()
            for (int i = 0; i < 5; i++) {
                column = new ArrayList<>()
                for (int j = 0; j < 5; j++) {
                    column.add(rows.get(j).numbers.get(i))
                }
                columns.add(new Panel(column))
            }
        }

        boolean mark(int number) {
            if (!alreadyWin) {
                lastMarked = number
                for (row in rows) {
                    if (row.mark(number)) {
                        alreadyWin = true
                        return true
                    }
                }
                for (column in columns) {
                    if (column.mark(number)) {
                        alreadyWin = true
                        return true
                    }
                }
            }

            return false
        }

        int calculateResult() {
            int sum = 0
            for (row in rows) {
                sum += row.sumUnmarked()
            }
            return sum * lastMarked
        }
    }

    private class Panel {
        List<Integer> numbers
        List<Integer> marked

        Panel(List<Integer> numbers) {
            this.numbers = numbers
            this.marked = new ArrayList<>()
        }

        boolean mark(int number) {
            for (n in numbers) {
                if (number == n) {
                    marked.add(number)
                }
            }
            if (numbers.size() == marked.size()) {
                return true
            }
            return false
        }

        int sumUnmarked() {
            def sum = 0
            for (number in numbers) {
                if (!marked.contains(number)) {
                    sum += number
                }
            }
            return sum
        }
    }
}
