# Assignment 3, Question 3: Tag Browser (Flow Layouts)

## Description
This project implements a dynamic "Tag Browser" screen using Jetpack Compose. It specifically focuses on mastering the `FlowRow` and `FlowColumn` layouts to create a responsive interface where elements wrap and stack intelligently based on content and screen constraints.

## Key Features

* **Dynamic Flow Layouts**: Implementation of `FlowRow` for responsive tag wrapping and `FlowColumn` with `maxItemsInEachColumn = 3` to create a structured vertical-to-horizontal filter grid.
* **Material 3 Interface**: Full utilization of M3 components including `Scaffold`, `CenterAlignedTopAppBar`, `FilterChip`, `InputChip`, `Card`, and `HorizontalDivider` for a modern, polished aesthetic.
* **Real-time State Management**: Uses `mutableStateListOf` to synchronize selections between the browser sections and the "Selected Tags" area, providing instant visual feedback upon user interaction.
* **Responsive Spacing**: Leverages `Arrangement.spacedBy(8.dp)` and `Modifier.padding()` to maintain consistent, adaptive gutter spacing across various screen orientations.
* **Layout Stability**: Employs a height-constrained `Box` wrapper for `FlowColumn` to ensure compatibility with `verticalScroll` modifiers and prevent measurement intrinsic crashes.

## Screenshots
![Tag Browser Screenshot](screenshot3.png)

## AI Disclosure (2 Points)
I used Google's Gemini AI to assist with layout debugging and rubric compliance:
1. **Layout Stability**: AI identified that placing a `FlowColumn` directly inside a `verticalScroll` Column causes a `MeasuringIntrinsics` crash. AI suggested providing fixed height constraints to the `FlowColumn` to resolve the measurement conflict.
2. **Component Usage**: AI helped distinguish between `FilterChip` (for general browsing) and `InputChip` (for selected items with trailing icons) to better align with Material 3 design patterns.
3. **Rubric Verification**: AI cross-checked the code to ensure that `Arrangement.spacedBy`, `FlowRow`, and `FlowColumn` were all explicitly utilized to meet the assignment's technical requirements.
