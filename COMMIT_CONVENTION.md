# Commit Convention

## Overview

This document defines the Git commit message conventions for this project to:
- Generate clear change history
- Facilitate automated Changelog generation
- Make code reviews more efficient

## Commit Format

Each commit message consists of the following structure:

```
<type>(<scope>): <subject>.

<body>

<footer>
```

- **type** (required): Commit type
- **scope** (optional): Scope of impact
- **subject** (required): Brief description, ending with a period
- **body** (optional): Detailed description
- **footer** (optional): Footer information

**Note**: A space is required after the colon.

## Commit Types

| Type | Description | Example |
|------|-------------|---------|
| `feature` | New feature | `feature: add phone verification login.` |
| `fix` | Bug fix | `fix: resolve order list refresh issue.` |
| `docs` | Documentation update | `docs: update build instructions.` |
| `refactor` | Code refactoring | `refactor: restructure user storage logic.` |
| `perf` | Performance improvement | `perf: optimize list scrolling smoothness.` |
| `model` | Data model changes | `model: add order status enum.` |
| `data` | Data layer changes | `data: migrate to new api response format.` |
| `prod` | Production configuration | `prod: update release signing config.` |

## Scope

Used to indicate the affected module or component:

| Scope | Description |
|-------|-------------|
| `app` | Application-wide changes |
| `login` | Login module |
| `order` | Order module |
| `profile` | Profile module |
| `nav` | Navigation related |
| `ui` | UI/Layout related |
| `data` | Data/Storage related |
| `build` | Build configuration |
| `deps` | Dependency updates |
| `readme` | README documentation |

## Subject

- Start with a **verb** in present tense ("add" not "added"/"adds")
- Do not capitalize the first letter
- Must end with a **period**
- No more than 50 characters (excluding period)
- A space after the colon is **required**

**Good Examples:**
- `feature: add country code selector.`
- `fix: resolve order detail crash.`
- `docs: update architecture documentation.`
- `refactor(login): extract validation logic.`

**Bad Examples:**
- `feature:add new feature.` (missing space after colon)
- `fix: resolve crash` (missing period)
- `Docs: update readme.` (capitalized first letter)
- `fix:修复崩溃问题.` (Chinese description)

## Body

Use when detailed explanation is needed:

- Explain **why** the change was made (motivation)
- Contrast with previous behavior
- No more than 72 characters per line
- Use blank lines to separate paragraphs

Example:
```
fix: resolve duplicate data in order list.

When quickly pulling down to refresh, async requests were not cancelled,
causing old and new data to return simultaneously, resulting in duplicates.

Now pending requests are cancelled before initiating new ones.
```

## Footer

Used for referencing Issues or Breaking Changes:

```
Closes #123, #456.

BREAKING CHANGE: login api response structure changed.
```

## Complete Examples

```
feature: implement phone verification login.

- Add phone number format validation.
- Integrate sms verification code api.
- Add 60-second countdown feature.

Closes #15.
```

```
refactor: encapsulate SharedPreferences as Repository pattern.

Change UserStorage from singleton object to UserRepository interface,
to support multiple storage methods (DataStore, Room, etc.) in the future.

BREAKING CHANGE: UserStorage is deprecated, use UserRepository instead.
```

## Android Project Specific

### Resource File Changes
```
feature(ui): add rounded button background.

Add corner radius and gradient color, support dark mode.
```

### Layout Changes
```
fix: resolve login page overflow on small screens.

Replace LinearLayout nesting with ConstraintLayout,
support automatic layout adjustment when keyboard appears.
```

### Dependency Updates
```
feature(deps): upgrade navigation component to 2.8.0.

- androidx.navigation:navigation-fragment-ktx: 2.7.7 -> 2.8.0.
- androidx.navigation:navigation-ui-ktx: 2.7.7 -> 2.8.0.
```

## Pre-Commit Checklist

- [ ] Commit message follows the format above
- [ ] Space after colon is included
- [ ] Subject ends with a period
- [ ] English description is used
- [ ] Correct type and scope are used
- [ ] Subject clearly describes the change
- [ ] Code is tested locally
- [ ] Related unit tests are updated/passed
- [ ] Changes reviewed with Claude Code

## Review Workflow

### Pre-commit Review
Before creating a git commit:
1. Review code changes with Claude Code
2. Confirm commit message format meets conventions
3. Get approval before executing `git commit`

### Pre-push Review
Before pushing to remote repository:
1. Confirm all commits are ready for sharing
2. Get approval before executing `git push`

This workflow ensures code quality and prevents accidental commits/pushes.

## Quick Reference

```bash
# Format
git commit -m "type(scope): subject."

# Examples
git commit -m "feature: add splash screen."
git commit -m "fix(login): resolve input validation bug."
git commit -m "docs: update commit convention."
```

---

*Reference: [Conventional Commits](https://www.conventionalcommits.org/)*
