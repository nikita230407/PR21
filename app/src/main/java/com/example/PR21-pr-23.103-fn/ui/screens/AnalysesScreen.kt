package com.example.pz21last.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

private data class Analysis(
    val title: String,
    val category: String,
    val time: String,
    val price: String,
    val description: String,
    val popular: Boolean = false
)

private val categories = listOf("Популярные", "COVID", "Комплексы", "Гормоны", "Витамины")

private val analyses = listOf(
    Analysis("Общий анализ крови", "Популярные", "1 день", "450 ₽", "Базовая оценка состояния организма", true),
    Analysis("Биохимия крови", "Популярные", "1-2 дня", "1 250 ₽", "Печень, почки, белки, глюкоза", true),
    Analysis("Общий анализ мочи", "Популярные", "1 день", "390 ₽", "Скрининг мочевыделительной системы",true),
    Analysis("ПЦР на COVID-19", "COVID", "24 часа", "1 600 ₽", "Выявление активной инфекции"),
    Analysis("Антитела IgG/IgM", "COVID", "1 день", "1 100 ₽", "Проверка иммунного ответа"),
    Analysis("Комплекс Check-up", "Комплексы", "2 дня", "3 900 ₽", "Расширенная программа обследования", true),
    Analysis("Аллергопанель", "Комплексы", "3 дня", "4 500 ₽", "Частые бытовые и пищевые аллергены"),
    Analysis("ТТГ", "Гормоны", "1 день", "620 ₽", "Главный показатель функции щитовидной железы"),
    Analysis("Т3 и Т4 свободные", "Гормоны", "1 день", "1 180 ₽", "Уточнение гормонального профиля"),
    Analysis("Витамин D", "Витамины", "1 день", "1 350 ₽", "Контроль дефицита витамина D"),
    Analysis("Витамин B12", "Витамины", "1 день", "980 ₽", "Диагностика причин слабости и анемии")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysesScreen() {
    var selectedTab by rememberSaveable { mutableStateOf(0) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var onlyPopular by rememberSaveable { mutableStateOf(false) }
    var sortByPrice by rememberSaveable { mutableStateOf(false) }
    var menuExpanded by remember { mutableStateOf(false) }

    val selectedCategory = categories[selectedTab]
    val filteredAnalyses = analyses
        .filter { it.category == selectedCategory }
        .filter { !onlyPopular || it.popular }
        .filter { it.title.contains(searchQuery, ignoreCase = true) || it.description.contains(searchQuery, ignoreCase = true) }
        .let { list ->
            if (sortByPrice) {
                list.sortedBy { it.price.filter(Char::isDigit).toIntOrNull() ?: 0 }
            } else {
                list
            }
        }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("МедЛаб", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                actions = {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Меню")
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(if (onlyPopular) "Показать все" else "Только популярные") },
                            leadingIcon = { Icon(Icons.Default.FilterList, contentDescription = null) },
                            onClick = {
                                onlyPopular = !onlyPopular
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(if (sortByPrice) "Обычный порядок" else "Сортировать по цене") },
                            leadingIcon = { Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = null) },
                            onClick = {
                                sortByPrice = !sortByPrice
                                menuExpanded = false
                            }
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding),
            contentPadding = PaddingValues(bottom = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { HeaderCard() }
            item {
                SearchField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it }
                )
            }
            item {
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    edgePadding = 16.dp,
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    categories.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis) }
                        )
                    }
                }
            }
            item {
                QuickActions(
                    onlyPopular = onlyPopular,
                    sortByPrice = sortByPrice,
                    onPopularClick = { onlyPopular = !onlyPopular },
                    onSortClick = { sortByPrice = !sortByPrice }
                )
            }
            item {
                Text(
                    text = "Доступные анализы",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            if (filteredAnalyses.isEmpty()) {
                item { EmptyResults(onReset = { searchQuery = ""; onlyPopular = false }) }
            } else {
                items(filteredAnalyses) { analysis ->
                    AnalysisCard(analysis = analysis)
                }
            }
        }
    }
}

@Composable
private fun HeaderCard() {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "Запись на лабораторные исследования",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Выберите категорию, найдите анализ и добавьте его в заявку.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.86f)
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatusPill("11 анализов")
                StatusPill("от 1 дня")
            }
        }
    }
}

@Composable
private fun StatusPill(text: String) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.18f))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text = text, color = Color.White, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
private fun SearchField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        singleLine = true,
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            if (value.isNotBlank()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Очистить")
                }
            }
        },
        placeholder = { Text("Поиск анализа или показателя") },
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
private fun QuickActions(
    onlyPopular: Boolean,
    sortByPrice: Boolean,
    onPopularClick: () -> Unit,
    onSortClick: () -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = onlyPopular,
                onClick = onPopularClick,
                label = { Text("Популярные") },
                leadingIcon = {
                    if (onlyPopular) Icon(Icons.Default.Done, contentDescription = null, modifier = Modifier.size(18.dp))
                }
            )
        }
        item {
            FilterChip(
                selected = sortByPrice,
                onClick = onSortClick,
                label = { Text("Сначала дешевле") },
                leadingIcon = { Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
        }
        item { AssistChip(onClick = {}, label = { Text("Работа с меню") }) }
    }
}

@Composable
private fun AnalysisCard(analysis: Analysis) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.LocalHospital,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(analysis.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Text(
                        analysis.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(analysis.price, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.AccessTime,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Готовность: ${analysis.time}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = {}) {
                    Icon(Icons.Default.AddShoppingCart, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("В заявку")
                }
            }
        }
    }
}

@Composable
private fun EmptyResults(onReset: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Ничего не найдено", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Измените запрос или сбросьте фильтры.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            TextButton(onClick = onReset) {
                Text("Сбросить")
            }
        }
    }
}
