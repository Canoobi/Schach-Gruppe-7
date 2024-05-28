# OOP-Projekt Gruppe 7 (Schach)

This repository contains a student chess project created for an ongoing lecture on object-oriented programming with Java
at HWR Berlin (summer term 2024).

> :warning: This code is for educational purposes only. Do not rely on it!

## Prerequisites

Installed:

1. IDE of your choice (e.g. IntelliJ IDEA)
2. JDK of choice installed (e.g. through IntelliJ IDEA)
3. Maven installed (e.g. through IntelliJ IDEA)
4. Git installed

## Local Development

This project uses [Apache Maven][maven] as build tool.

To build from your shell (without an additional local installation of Maven), ensure that `./mvnw`
is executable:

```
chmod +x ./mvnw
```

I recommend not to dive into details about Maven at the beginning.
Instead, you can use [just][just] to build the project.
It reads the repositories `justfile` which maps simplified commands to corresponding sensible Maven
calls.

With _just_ installed, you can simply run this command to perform a build of this project and run
all of its tests:

```
just build
```

## Abstract

# Short description of our project:

Chess game, programmed by 4 students at HWR Berlin in OOP Lecture.

# Most important features:

- chess logic
- cli
- persistance

# Most interesting problems we encountered during the project:

- testing exceptions
- writing into a csv-file

## Feature List

### Backend

| Number | Feature      | Implemented             | Tested             |
|--------|--------------|-------------------------|--------------------|
| 1      | Chessboard   | :heavy_check_mark:      | :heavy_check_mark: |
| 2      | Pieces       | :heavy_check_mark:      | :heavy_check_mark: |
| 3      | Movement     | :heavy_check_mark:      | :heavy_check_mark: |
| 4      | Check        | :ballot_box_with_check: | :heavy_check_mark: |
| 5      | Checkmate    | :heavy_check_mark:      | :heavy_check_mark: |
| 6      | Game         | :heavy_check_mark:      | :heavy_check_mark: |
| 7      | FEN Notation | :heavy_check_mark:      | :heavy_check_mark: |

### Command Line Interface

| Number | Feature       | Implemented        | Tested             |
|--------|---------------|--------------------|--------------------|
| 1      | New Game      | :heavy_check_mark: | :heavy_check_mark: |
| 2      | Play on Board | :heavy_check_mark: | :heavy_check_mark: |
| 3      | Game state    | :heavy_check_mark: | :heavy_check_mark: |
| 4      | Help          | :heavy_check_mark: | :heavy_check_mark: |
| 5      | Offer Remis   | :heavy_check_mark: | :heavy_check_mark: |

### Persistence

| Number | Feature     | Implemented        | Tested             |
|--------|-------------|--------------------|--------------------|
| 1      | Save Game   | :heavy_check_mark: | :heavy_check_mark: |
| 2      | Load Game   | :heavy_check_mark: | :heavy_check_mark: |
| 2      | Delete Game | :x:                | :x:                |

## Additional Dependencies

| Number | Dependency Name | Dependency Description | Why is it necessary? |
|--------|-----------------|------------------------|----------------------|
| 1      | :x:             | :x:                    | :x:                  |

### Multiple remote repositories

Your local repository should have a reference to both the fork (your own remote repository)
and the original remote repository.
To configure your git remote repositories, use the `git remote` command set.

1. Clone your fork and go enter the repository.

```
git clone <fork-url>
cd <created-folder>
```

2. Now your fork is configured as primary remote repository (origin).
   Next to origin, you should add the original repository as a second remote repository (upstream).

```
git remote add upstream <repository-url>
```

3. Verify that both remotes are configured correctly.
   The following command should list both remotes: origin and upstream.

```
git remote -v
```

4. To fetch changes from all remote repositories, use:

```
git fetch --all
```

5. If there are interesting changes (in e.g. the `main` branch) to merge into your branch, use:

```
git pull upstream main
```

[maven]: https://maven.apache.org/

[just]: https://github.com/casey/just
