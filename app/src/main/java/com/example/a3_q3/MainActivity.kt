package com.example.a3_q3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TagBrowserScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagBrowserScreen() {
    val scrollState = rememberScrollState()

    val techTags = listOf("Kotlin", "Compose", "Android", "iOS", "React", "Flutter", "Swift", "TypeScript", "Python", "Rust", "Go", "C++")
    val designTags = listOf("UI/UX", "Material Design", "Figma", "Accessibility", "Animation", "Typography")
    val topicTags = listOf("Machine Learning", "Cloud", "DevOps", "Security", "Backend", "Frontend", "Mobile", "Data Science")

    val selectedTags = remember { mutableStateListOf<String>() }

    val sortOptions = listOf("Newest", "Popular", "Alphabetical", "Most Used")
    var activeSortOption by remember { mutableStateOf("Popular") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tag Browser") },
                actions = {
                    IconButton(onClick = { selectedTags.clear() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset")
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
        ) {

            // --- Selected tags area ---
            Text(
                "Selected (${selectedTags.size})",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(top = 8.dp, bottom = 6.dp)
            )

            if (selectedTags.isEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Text(
                        "Tap tags below to add them here",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        val currentTags = selectedTags.toList()
                        // show selected chips in rows of 3
                        currentTags.chunked(3).forEach { rowTags ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                rowTags.forEach { tag ->
                                    SuggestionChip(
                                        onClick = { selectedTags.remove(tag) },
                                        label = {
                                            Row {
                                                Text(tag)
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Icon(
                                                    Icons.Default.Close,
                                                    contentDescription = "Remove",
                                                    modifier = Modifier.size(14.dp)
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // --- Sort & Filter section (column-style grid) ---
            Text(
                "Sort & Filter",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                // 2x2 grid using Row + Column
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        sortOptions.take(2).forEach { option ->
                            SortFilterChip(
                                label = option,
                                isSelected = activeSortOption == option,
                                onClick = { activeSortOption = option }
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        sortOptions.drop(2).forEach { option ->
                            SortFilterChip(
                                label = option,
                                isSelected = activeSortOption == option,
                                onClick = { activeSortOption = option }
                            )
                        }
                    }
                }
            }

            // --- Tag sections ---
            TagSection("Technology", techTags, selectedTags) { tag ->
                if (selectedTags.contains(tag)) selectedTags.remove(tag) else selectedTags.add(tag)
            }
            Spacer(modifier = Modifier.height(16.dp))

            TagSection("Design", designTags, selectedTags) { tag ->
                if (selectedTags.contains(tag)) selectedTags.remove(tag) else selectedTags.add(tag)
            }
            Spacer(modifier = Modifier.height(16.dp))

            TagSection("Topics", topicTags, selectedTags) { tag ->
                if (selectedTags.contains(tag)) selectedTags.remove(tag) else selectedTags.add(tag)
            }
            Spacer(modifier = Modifier.height(24.dp))

            // --- Search button ---
            if (selectedTags.isNotEmpty()) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Search with ${selectedTags.size} tags")
                }
            }
        }
    }
}

@Composable
fun SortFilterChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(label) },
        leadingIcon = if (isSelected) {
            {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        } else null,
        modifier = if (isSelected) {
            Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
        } else {
            Modifier.clip(RoundedCornerShape(8.dp))
        }
    )
}

@Composable
fun TagSection(
    title: String,
    tags: List<String>,
    selectedTags: List<String>,
    onTagClick: (String) -> Unit
) {
    Text(
        title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    // simulate flow wrapping with chunked rows of 4
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.chunked(4).forEach { rowTags ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowTags.forEach { tag ->
                    val isSelected = selectedTags.contains(tag)
                    FilterChip(
                        selected = isSelected,
                        onClick = { onTagClick(tag) },
                        label = { Text(tag) },
                        leadingIcon = if (isSelected) {
                            {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        } else null,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = if (isSelected) {
                            Modifier.border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                        } else Modifier
                    )
                }
            }
        }
    }
}