package com.ysrtyfn.kadimelifbatalimler.ui.sahneUnsurlari

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Scoreboard
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ysrtyfn.kadimelifbatalimler.R
import com.ysrtyfn.kadimelifbatalimler.datalar.UstIdareCubuguData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UstIdareCubugu(ustIdareCubuguData: UstIdareCubuguData) {

    Column(
        modifier = Modifier.fillMaxWidth().requiredHeight(57.dp).padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth().requiredHeight(56.dp),
            title = {
                if(ustIdareCubuguData.baslikGosterilsinMI)
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = stringResource(ustIdareCubuguData.ustIdareCubuguBaslikMetniID),
    //                textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = if(!ustIdareCubuguData.baslikMetniKucukMu) MaterialTheme.typography.titleLarge
                                    else MaterialTheme.typography.titleSmall
                        )
                    }
            },
            navigationIcon = {
                if (ustIdareCubuguData.merkeziSahneTusuGosterilsinMI) {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = ustIdareCubuguData.merkeziSahneTusuOnClick,
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        ) {
                            Icon(
                                Icons.Rounded.Home,
                                contentDescription = stringResource(ustIdareCubuguData.merkeziSahneTusuTarifMetniID),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            },
            actions = {
                val iconSize = 24.dp

                if (ustIdareCubuguData.rehberTusuGosterilsinMI) {
                    IconButton(
                        modifier = Modifier.fillMaxHeight(),
                        onClick = {
                            ustIdareCubuguData.rehberTusuOnClick()
                        },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        Icon(modifier = Modifier.requiredSize(iconSize),
                            painter = painterResource(id = R.drawable.rehber),
                            contentDescription = stringResource(id = ustIdareCubuguData.rehberTusuTarifMetniID) ,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (ustIdareCubuguData.cetelePenceresiTusuGosterilsinMI) {
                    IconButton(
                        modifier = Modifier.fillMaxHeight(),
                        onClick = {
                            ustIdareCubuguData.cetelePenceresiTusuOnClick()
                        },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        Icon(modifier = Modifier.requiredSize(iconSize),
                            imageVector = Icons.Rounded.Scoreboard,
                            contentDescription = stringResource(id = ustIdareCubuguData.cetelePenceresiTusuTarifMetniID) ,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (ustIdareCubuguData.tasnifTusuGosterilsinMI) {
                    IconButton(
                        modifier = Modifier.fillMaxHeight(),
                        onClick = {
                            ustIdareCubuguData.tasnifTusuOnClick()
                        },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        Icon(modifier = Modifier.requiredSize(iconSize),
                            imageVector = Icons.Rounded.Category,
                            contentDescription = stringResource(id = ustIdareCubuguData.tasnifTusuTarifMetniID) ,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (ustIdareCubuguData.tercihlerTusuGosterilsinMI) {
                    IconButton(
                        modifier = Modifier.fillMaxHeight(),
                        onClick = {
                            ustIdareCubuguData.tercihlerTusuOnClick()
                        },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        Icon(modifier = Modifier.requiredSize(iconSize),
                            imageVector = Icons.Rounded.Settings,
                            contentDescription = stringResource(id = ustIdareCubuguData.tercihlerTusuTarifMetniID) ,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            )
        )

        Divider(modifier = Modifier.fillMaxWidth().requiredHeight(1.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant)
    }

}